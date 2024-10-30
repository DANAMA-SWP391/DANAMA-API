CREATE DATABASE DANAMA;
GO

USE DANAMA;
GO

-- Create User Table
CREATE TABLE Account
(
    [UID]      INT
        CONSTRAINT PK_Account_UID PRIMARY KEY IDENTITY (1,1),
    [name]     NVARCHAR(255) NOT NULL,
    [email]    VARCHAR(255)  NOT NULL UNIQUE,
    [phone]    VARCHAR(15),
    [avatar]   VARCHAR(255),
    [googleId] VARCHAR(255),
    [roleId]   INT,
    [password] VARCHAR(255)
);

-- Create Genre Table
CREATE TABLE Genre
(
    genreId INT
        CONSTRAINT PK_Genre_genreId PRIMARY KEY IDENTITY (1,1),
    [name]  NVARCHAR(255) NOT NULL
);

-- Create Movie Table
CREATE TABLE Movie
(
    movieId       INT
        CONSTRAINT PK_Movie_movieId PRIMARY KEY IDENTITY (1,1),
    [name]        NVARCHAR(255) NOT NULL,
    [description] NVARCHAR(255),
    poster        VARCHAR(255),
    trailer       VARCHAR(255),
    releasedate   DATE,
    country       VARCHAR(255),
    director      NVARCHAR(255),
    agerestricted INT,
    actors        NVARCHAR(255),
    duration      INT,
    status        INT
);

-- Create MovieGenre Table
CREATE TABLE MovieGenre
(
    movieId INT,
    genreId INT,
    CONSTRAINT PK_MovieGenre_movieId_genreId PRIMARY KEY (movieId, genreId),
    CONSTRAINT FK_MovieGenre_Genre_genreId FOREIGN KEY (genreId) REFERENCES Genre (genreId),
    CONSTRAINT FK_MovieGenre_Movie_movieId FOREIGN KEY (movieId) REFERENCES Movie (movieId)
);

-- Create Review Table
CREATE TABLE Review
(
    reviewId INT
        CONSTRAINT PK_Review_reviewId PRIMARY KEY IDENTITY (1,1),
    rating   INT,
    comment  NVARCHAR(255),
    [date]   DATETIME,
    [UID]    INT,
    movieId  INT,
    CONSTRAINT FK_Review_Account_UID FOREIGN KEY ([UID]) REFERENCES Account ([UID]),
    CONSTRAINT FK_Review_Movie_movieId FOREIGN KEY (movieId) REFERENCES Movie (movieId)
);

-- Create Cinema Table
CREATE TABLE Cinema
(
    cinemaId      INT
        CONSTRAINT PK_Cinema_cinemaId PRIMARY KEY IDENTITY (1,1),
    [name]        NVARCHAR(255) NOT NULL,
    logo          VARCHAR(255),
    [address]     NVARCHAR(255),
    [description] NVARCHAR(255),
    [image]       VARCHAR(255),
    managerId     INT,
    CONSTRAINT FK_Cinema_Account_managerId FOREIGN KEY (managerId) REFERENCES Account ([UID])
);

-- Create Room Table
CREATE TABLE Room
(
    roomId   INT
        CONSTRAINT PK_Room_roomId PRIMARY KEY IDENTITY (1,1),
    [name]   NVARCHAR(255),
    cinemaId INT,
    CONSTRAINT FK_Room_Cinema_cinemaId FOREIGN KEY (cinemaId) REFERENCES Cinema (cinemaId)
);

-- Create Seat Table
CREATE TABLE Seat
(
    seatId  INT
        CONSTRAINT PK_Seat_seatId PRIMARY KEY IDENTITY (1,1),
    seatNum VARCHAR(3),
    col     INT,
    [row]   INT,
    [type]  VARCHAR(50),
    roomId  INT,
    status  INT,
    CONSTRAINT FK_Seat_Room_roomId FOREIGN KEY (roomId) REFERENCES Room (roomId) ON DELETE SET NULL
);

-- Create Showtime Table
CREATE TABLE Showtime
(
    showtimeId INT
        CONSTRAINT PK_Showtime_showtimeId PRIMARY KEY IDENTITY (1,1),
    showdate   DATE,
    starttime  TIME,
    endtime    TIME,
    baseprice  DECIMAL(10, 2),
    movieId    INT,
    roomId     INT,
    status     INT,
    CONSTRAINT FK_Showtime_Movie_movieId FOREIGN KEY (movieId) REFERENCES Movie (movieId),
    CONSTRAINT FK_Showtime_Room_roomId FOREIGN KEY (roomId) REFERENCES Room (roomId)
);

-- Create Booking Table
CREATE TABLE Booking
(
    bookingId   INT
        CONSTRAINT PK_Booking_bookingId PRIMARY KEY IDENTITY (1,1),
    totalcost   DECIMAL(10, 2),
    [timestamp] DATETIME,
    [UID]       INT,
    status      INT,
    CONSTRAINT FK_Booking_Account_UID FOREIGN KEY ([UID]) REFERENCES Account ([UID])
);

-- Create Ticket Table
CREATE TABLE Ticket
(
    ticketId   INT
        CONSTRAINT PK_Ticket_ticketId PRIMARY KEY IDENTITY (1,1),
    price      DECIMAL(10, 2),
    [name]     NVARCHAR(255),
    email      VARCHAR(255),
    phone      VARCHAR(15),
    bookingId  INT,
    showtimeId INT,
    seatId     INT,
    CONSTRAINT UC_Ticket UNIQUE (showtimeId, seatId),
    CONSTRAINT FK_Ticket_Booking_bookingId FOREIGN KEY (bookingId) REFERENCES Booking (bookingId),
    CONSTRAINT FK_Ticket_Showtime_showtimeId FOREIGN KEY (showtimeId) REFERENCES Showtime (showtimeId),
    CONSTRAINT FK_Ticket_Seat_seatId FOREIGN KEY (seatId) REFERENCES Seat (seatId)
);

-- Create MovieRequest Table without ON DELETE CASCADE and allowing movieId to be NULL
CREATE TABLE MovieRequest (
                              requestId INT PRIMARY KEY IDENTITY (1,1),
                              cinemaId INT NOT NULL,
                              movieId INT NULL, -- Allow NULL for movieId to keep MovieRequest when Movie is deleted
                              status TINYINT NOT NULL, -- Status of the request: 0 = pending, 1 = approved, 2 = rejected
                              message VARCHAR(255), -- Message from the cinema manager
                              timestamp DATETIME DEFAULT GETDATE(), -- Request creation time
                              FOREIGN KEY (cinemaId) REFERENCES Cinema (cinemaId),
                              FOREIGN KEY (movieId) REFERENCES Movie (movieId) ON DELETE SET NULL -- Set to NULL instead of delete
);




