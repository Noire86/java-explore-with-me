CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                            NOT NULL,
    email VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(32)                             NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title              VARCHAR(128)                            NOT NULL,
    annotation         TEXT                                    NOT NULL,
    category_id        BIGINT,
    description        TEXT,
    date_event         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    created            TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    confirmed_requests INTEGER,
    paid               BOOLEAN,
    participant_limit  INTEGER DEFAULT 0,
    published          TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN DEFAULT TRUE,
    location_lat       DOUBLE,
    location_lon       DOUBLE,
    state              VARCHAR(20),
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT FK_EVENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT FK_EVENT_ON_USER FOREIGN KEY (initiator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       VARCHAR(20),
    CONSTRAINT pk_request PRIMARY KEY (id),
    CONSTRAINT UQ_EVENT_REQUESTER UNIQUE (event_id, requester_id),
    CONSTRAINT FK_PARTICIPATION_REQUEST_ON_EVENT FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT FK_PARTICIPATION_REQUEST_ON_REQUESTER FOREIGN KEY (requester_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title  VARCHAR(128)                            NOT NULL,
    pinned BOOLEAN,
    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event_compilation
(
    event_id       BIGINT NOT NULL,
    compilation_id BIGINT NOT NULL,
    CONSTRAINT pk_event_compilation PRIMARY KEY (event_id, compilation_id),
    CONSTRAINT FK_EVENT_COMPILATION_ON_EVENT FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT FK_EVENT_COMPILATION_ON_COMPILATION FOREIGN KEY (compilation_id) REFERENCES compilations (id)
);


