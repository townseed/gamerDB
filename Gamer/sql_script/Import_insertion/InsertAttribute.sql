CREATE PROCEDURE InsertAttribute
@AttributeName VARCHAR(20)
AS
BEGIN
    INSERT INTO attribute ([name])
    VALUES (@AttributeName);
END;
