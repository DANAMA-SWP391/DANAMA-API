create database DANAMA
go
use  DANAMA
go
-- Create User Table
CREATE TABLE Account (
    [UID] INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(255) NOT NULL,
    [email] VARCHAR(255) NOT NULL UNIQUE,
    [phone] VARCHAR(15),
    [avatar] VARCHAR(255),
    [googleId] VARCHAR(255),
	[roleId] INT,
    [password] VARCHAR(255)
)
-- Create Genre Table
CREATE TABLE Genre (
    genreId INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(255) NOT NULL
);

-- Create Movie Table
CREATE TABLE Movie (
    movieId INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(255) NOT NULL,
    [description] TEXT,
    poster VARCHAR(255),
    trailer VARCHAR(255),
    releasedate DATE,
    country VARCHAR(255),
    director NVARCHAR(255),
    agerestricted INT,
    actors TEXT,
    duration INT
);

--Create MovieGenre table
CREATE TABLE MovieGenre (
	movieId INT,
	genreId INT,
	PRIMARY KEY (movieId,genreId),
	FOREIGN KEY (genreId) REFERENCES Genre(genreId),
	FOREIGN KEY (movieId) REFERENCES Movie(movieId)
)

-- Create Review Table
CREATE TABLE Review (
    reviewId INT PRIMARY KEY IDENTITY(1,1),
    rating INT,
    comment TEXT,
    [date] DATETIME,
    [UID] INT,
	movieId INT,
    FOREIGN KEY ([UID]) REFERENCES Account([UID]),
	FOREIGN KEY (movieId) REFERENCES Movie(movieId)
);

-- Create Cinema Table
CREATE TABLE Cinema (
    cinemaId INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(255) NOT NULL,
    logo VARCHAR(255),
    [address[ TEXT,
    [description] TEXT,
    [image] VARCHAR(255),
	managerId int,
	FOREIGN KEY (managerId) REFERENCES Account([UID])
);

-- Create Room Table
CREATE TABLE Room (
    roomId INT PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(255),
    numberOfSeat INT,
    cinemaId INT,
    FOREIGN KEY (cinemaId) REFERENCES Cinema(cinemaId)
);

-- Create Seat Table
CREATE TABLE Seat (
    seatId INT PRIMARY KEY IDENTITY(1,1),
    seatNum VARCHAR(3),
    col INT,
    [row] INT,
    [type] VARCHAR(50),
    roomId INT,
    FOREIGN KEY (roomId) REFERENCES Room(roomId)
);

-- Create Showtime Table
CREATE TABLE Showtime (
    showtimeId INT PRIMARY KEY IDENTITY(1,1),
    showdate DATE,
    starttime TIME,
    endtime TIME,
    baseprice DECIMAL(10, 2),
    movieId INT,
    roomId INT,
	seatAvailable INT,
    FOREIGN KEY (movieId) REFERENCES Movie(movieId),
    FOREIGN KEY (roomId) REFERENCES Room(roomId)
)
-- Create SeatAvailability Table
CREATE TABLE SeatAvailability (
    seatId INT,
    showtimeId INT,
    isAvailability bit NOT NULL,
    PRIMARY KEY (seatId, showtimeId),
    FOREIGN KEY (seatId) REFERENCES Seat(seatId),
    FOREIGN KEY (showtimeId) REFERENCES Showtime(showtimeId)
);

-- Create Booking Table
CREATE TABLE Booking (
    bookingId INT PRIMARY KEY IDENTITY(1,1),
    totalcost DECIMAL(10, 2),
    [timestamp] DATETIME,
    [UID] INT,
    FOREIGN KEY ([UID]) REFERENCES Account([UID])
);

-- Create Ticket Table
CREATE TABLE Ticket (
    ticketId INT PRIMARY KEY IDENTITY(1,1),
    price DECIMAL(10, 2),
    [name] NVARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(15),
    bookingId INT,
	showtimeId INT,
	seatId INT,
    FOREIGN KEY (bookingId) REFERENCES Booking(bookingId),
	FOREIGN KEY (showtimeId) REFERENCES Showtime(showtimeId),
	FOREIGN KEY (seatId) REFERENCES Seat(seatId)
);
