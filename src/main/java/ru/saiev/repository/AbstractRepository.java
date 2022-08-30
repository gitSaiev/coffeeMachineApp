package ru.saiev.repository;

import ru.saiev.config.DBConnectionConfig;

public abstract class AbstractRepository {
    public final DBConnectionConfig dataBaseConnection = new DBConnectionConfig();
}
