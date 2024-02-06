CREATE or alter PROCEDURE InsertMatch
@Date DATE,
@Location VARCHAR(30)
AS
BEGIN
    INSERT INTO [match] ([date], [location])
    VALUES (@Date, @Location);
END


