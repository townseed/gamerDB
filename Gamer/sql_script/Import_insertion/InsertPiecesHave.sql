CREATE PROCEDURE InsertPiecesHave
@GameID INT,
@AttributeID INT
AS
BEGIN
    INSERT INTO pieceshave (gameID, attributeID)
    VALUES (@GameID, @AttributeID);
END;
