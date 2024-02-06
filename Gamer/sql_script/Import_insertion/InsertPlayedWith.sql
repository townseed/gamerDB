CREATE PROCEDURE InsertPlayedWith
@GameName VARCHAR(20),
@GamePieceID INT
AS
BEGIN
    INSERT INTO playedwith (gamename, gamepieceid)
    VALUES (@GameName, @GamePieceID);
END;
