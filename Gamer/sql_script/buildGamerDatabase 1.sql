use games
go

--create the object tables

create table gamer
(
id int identity(1,1) primary key,
Username varchar(10) not null,
[name] varchar(20) not null,
email varchar(30),
DateOfBirth date,
constraint UNIQUE_USERNAME unique(username)
)

create table [match]
(
id int identity(1,1) primary key,
[date] date not null,
[location] varchar(30) not null
)

create table game
(
[name] varchar(20) primary key
)

create table gamepiece
(
[name] varchar(20),
id int identity(1,1) primary key
)

create table attribute
(
[name] varchar(20) primary key
)

--create the relationship tables

create table friendof
(
gamer1id int,
gamer2id int,
constraint PrimaryKey
primary key(gamer1id, gamer2id),
constraint ForeignKeyFriendof1 foreign key (gamer1id) references gamer (id),
constraint ForeignKeyFriendof2 foreign key (gamer2id) references gamer (id),
constraint FriendSelf check(gamer1id != gamer2id)
)

create table playedIN
(
gamerID int,
matchID int,
result bit, --1 if win 0 if loss null if tie
score int,
primary key(gamerID, matchID),
constraint ForeignKeyGamer foreign key (gamerID) references gamer (id),
constraint ForeignKeyPlayedINMatch foreign key (matchID) references [match] (id)
)

create table used
(
matchid int,
gamename varchar(20),
gamepieceid int,
primary key(matchid, gamename),
constraint ForeignKeyUsedInMatch foreign key (matchID) references [match] (id),
constraint ForeignKeyUsedInGame foreign key (gamename) references game ([name]),
constraint ForeignKeyUsedPiece foreign key (gamepieceid) references gamepiece (id)
)

create table playedwith
(
gamename varchar(20),
gamepieceid int,
constraint ForeignKeyPlayedWithGame foreign key (gamename) references game ([name]),
constraint ForeignKeyPlayedWithPiece foreign key (gamepieceid) references gamepiece (id)
)

create table pieceshave --this table discribes what attributes the pieces within a given game have. ex mtg decks have mana value and color while rps has one 'beats' attribute
(
gamename varchar(20) not null,
attributename varchar(20) not null,
primary key (gamename, attributename),
constraint ForeignKeyPiecesHaveGame foreign key (gamename) references game ([name]),
constraint ForeignKeyPiecesHaveAttribute foreign key (attributename) references attribute ([name])
)

create table gamepieceHas --this table discribes the attributes that each gamepiece has, ie naming that rock beats scissors
(
value varchar(30) not null, --this is where the data will go, it will need to be parsed later from strings if other datatypes are used
attributename varchar(20) not null,
gamepieceID int not null,
primary key (gamepieceID, attributeName),
constraint ForeignKeyGamepieceHas foreign key (gamepieceID) references gamepiece (id),
constraint ForeignKeyGamepieceHasAttribute foreign key (attributename) references attribute ([name])
)