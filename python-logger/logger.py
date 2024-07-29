import json
import logging
import sys
from logging.handlers import TimedRotatingFileHandler
from typing import Optional, Union
from pathlib import Path
from discordapi import DiscordApi

AVAILABLE_LOGGER_LEVEL: list[str] = [
    "NOTSET",
    "DEBUG",
    "INFO",
    "WARN",
    "ERROR",
    "CRITICAL",
]

AVAILABLE_ROTATION_WHEN: list[str] = [
    "S",  # seconds
    "M",  # minutes
    "H",  # hours
    "D",  # days
    "W0",  # mon
    "W1",  # tues
    "W2",  # wed
    "W3",  # thurs
    "W4",  # fri
    "W5",  # sat
    "W6",  # sun
    "midnight",  # roll over at midnight, if atTime not specified
]


class CustomFormatter(logging.Formatter):
    """Custom formatter, overrides funcName with value of name_override if it exists
    Source: https://stackoverflow.com/questions/7003898/using-functools-wraps-with-a-logging-decorator
    """

    def format(self, record: logging.LogRecord):
        if hasattr(record, "name_override"):
            record.funcName = record.name_override
        return super().format(record)


class DiscordBotHandler(logging.Handler):
    def __init__(self, webhook: str, app_name: str):
        self.discord_api = DiscordApi(webhook=webhook)
        self.app_name: str = app_name
        self.counter: int = 0
        super().__init__()

    def emit(self, record: logging.LogRecord):
        # log_entry = self.format(record=record)
        log_entry: dict = self.__parse_discord_data(record=record)
        self.discord_api.call_api_endpoint(data=log_entry)

    def __parse_discord_data(self, record: logging.LogRecord):
        description: str = self.__build_description(record=record)
        log_entry: dict = {
            # for all params, see https://discordapp.com/developers/docs/resources/webhook#execute-webhook
            "content": f"Log level: {record.levelname}",
            "username": f"App name: {self.app_name}",
            # leave this out if you dont want an embed
            # for all params, see https://discordapp.com/developers/docs/resources/channel#embed-object
            "embeds": [
                {
                    "description": description,
                    "title": f"Calling from {record.filename} task count: `{self.counter}`",
                }
            ],
        }
        self.counter += 1
        return log_entry

    def __build_description(self, record: logging.LogRecord):
        description: dict = {}
        if "data" in record.__dict__:
            description.update(record.data)
        description.update({"message": record.message})
        return json.dumps(description)


def log_setup(
    parent_dir: Union[str, Path],
    log_filename: str,
    logger_level: Union[str, int],
    log_stdout: bool = True,
    log_name: Optional[str] = None,
    provider: Optional[str] = None,
    webhook: Optional[str] = None,
    rotation_when: Optional[str] = None,
    rotation_interval: int = 1,
    rotation_backupCount: int = 0,
) -> logging.Logger:
    _parent_dir: Path = Path(parent_dir) if isinstance(parent_dir, str) else parent_dir
    _logs_path: Path = Path(f"{_parent_dir}/logs/")

    if not _logs_path.exists():
        _logs_path.mkdir(parents=True, exist_ok=True)
    log_format: str = (
        "%(asctime)s [%(levelname)s] %(filename)s:%(lineno)d - %(funcName)s - %(message)s"
    )
    formatter: CustomFormatter = CustomFormatter(
        fmt=log_format, datefmt="%Y-%m-%d %I:%M:%S %p"
    )

    logger: Union[logging.RootLogger, logging.Logger] = (
        logging.getLogger(log_name) if log_name else logging.getLogger()
    )

    _logger_level: str = (
        logger_level.upper()
        if logger_level.upper() in AVAILABLE_LOGGER_LEVEL
        else "INFO"
    )
    logger.setLevel(_logger_level)

    # rotation interval setup
    _rotation_when: str = (
        rotation_when if rotation_when in AVAILABLE_ROTATION_WHEN else "midnight"
    )
    _log_filename_path: Path = Path(f"{_logs_path}/{log_filename}.log")
    handler = TimedRotatingFileHandler(
        _log_filename_path,
        when=_rotation_when,
        interval=rotation_interval,
        backupCount=rotation_backupCount,
    )

    handler.setFormatter(formatter)
    logger.addHandler(handler)

    # output to console
    if log_stdout:
        console = logging.StreamHandler(sys.stdout)
        console.setLevel(logger_level)
        console.setFormatter(formatter)
        logger.addHandler(console)

    if provider:
        match provider.lower():
            case "discord":
                logger.info("activating discord handler")
                discord_handler = DiscordBotHandler(
                    webhook=webhook, app_name=log_filename
                )
                discord_handler.setLevel(logger_level)
                logger.addHandler(discord_handler)
    else:
        logger.info("no provider specified")

    return logger
