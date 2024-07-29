from pathlib import Path
from logger import log_setup
import logging
import time


def main():
    for i in range(0, 5):
        extra = {"data": {"test_key": "test_value"}}
        logger.info("discord info", extra=extra)
        # logger.debug("This is a debug message")
        # logger.info("This is an info message")
        # logger.warning("This is a warning message")
        # logger.error("This is an error message")
        # logger.critical("This is a critical message")
        print(i)
        time.sleep(1)


if __name__ == "__main__":
    workdir: Path = Path(__file__).resolve().parent
    logdir: Path = Path(f"{workdir}/")

    webhook = "your_web_token"

    logger: logging.Logger = log_setup(
        parent_dir=workdir,
        log_filename=workdir.name,
        logger_level="INFO",
        rotation_when="midnight",
        rotation_interval=1,
        rotation_backupCount=15,
        provider="discord",
        webhook=webhook,
    )
    main()
