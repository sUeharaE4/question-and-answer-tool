CREATE TABLE tool (
  id BIGSERIAL PRIMARY KEY
  , name varchar(64)
  , description varchar(1024)
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  , updated_at TIMESTAMP WITH TIME ZONE
);
