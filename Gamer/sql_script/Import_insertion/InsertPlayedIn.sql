CREATE PROCEDURE InsertPlayedIn
@GamerID INT,
@MatchID INT,
@Result BIT,
@Score INT
AS
BEGIN
    INSERT INTO playedIN (gamerID, matchID, result, score)
    VALUES (@GamerID, @MatchID, @Result, @Score);
END;
