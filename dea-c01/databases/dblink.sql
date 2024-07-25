create extension postgres_fdw;
create extension dblink;
create server foreign_server
    foreign data wrapper postgres_fdw
    options(host '{{ redshift_ip }}', port '{{ port }}', dbname '{{ database_name }}', sslmode 'require');
create user mapping for {{ rds_postgresql_username }}
    server foreign_server
    options (user '{{ redshift_username }}', password '{{ password }}');
