CREATE PROCEDURE InsertGamePieceHas
@Value VARCHAR(30),
@AttributeID INT,
@GamePieceID INT
AS
BEGIN
    INSERT INTO gamepieceHas (value, attributeID, gamepieceID)
    VALUES (@Value, @AttributeID, @GamePieceID);
END;
