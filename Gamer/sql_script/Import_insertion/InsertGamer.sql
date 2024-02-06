CREATE or alter PROCEDURE InsertGamer
@Username VARCHAR(10),
@Name VARCHAR(20),
@Email VARCHAR(30),
@DateOfBirth DATE
AS
BEGIN
    INSERT INTO gamer (Username, [name], email, DateOfBirth)
    VALUES (@Username, @Name, @Email, @DateOfBirth);
END;
