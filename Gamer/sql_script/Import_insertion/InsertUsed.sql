CREATE PROCEDURE InsertUsed
@MatchID INT,
@Gameid int ,
@GamePieceID INT
AS
BEGIN
    INSERT INTO used (matchid, gameid, gamepieceid)
    VALUES (@MatchID, @Gameid, @GamePieceID);
END;

