CREATE or alter PROCEDURE InsertGamePieces
@GamePieceName VARCHAR(20)
AS
BEGIN
	IF Exists(select * from gamepieces where name = @GamePiecename)
	BEGIN
		RAISERROR('cannot insert repeated gamePieces', 14, 1)
		RETURN
	END

    INSERT INTO [gamepieces] ([id], [name])
    VALUES ('temp_id', @GamePieceName);
END;
