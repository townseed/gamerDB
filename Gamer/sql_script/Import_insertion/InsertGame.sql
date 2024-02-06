CREATE PROCEDURE InsertGame
@GameName VARCHAR(20)
AS
BEGIN
    INSERT INTO game ([name])
    VALUES (@GameName);
END;
