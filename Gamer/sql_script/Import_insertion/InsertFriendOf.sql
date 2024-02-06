CREATE PROCEDURE InsertFriendOf
@Gamer1ID INT,
@Gamer2ID INT
AS
BEGIN
    INSERT INTO friendof (gamer1id, gamer2id)
    VALUES (@Gamer1ID, @Gamer2ID);
END;
