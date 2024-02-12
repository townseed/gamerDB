CREATE or alter PROCEDURE InsertGamer
@Username VARCHAR(10),
@Name VARCHAR(20),
@Email VARCHAR(30),
@DateOfBirth DATE
AS
BEGIN
    DELETE FROM gamer WHERE Username = @Username;
END;
