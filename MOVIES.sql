CREATE DATABASE MOVIES
GO
USE MOVIES
GO

-------------------------- Kreiranje tablica --------------------------
CREATE TABLE Users
(
	IDUsers INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(25),
	Password NVARCHAR(100),
	MemberRole TINYINT, CONSTRAINT CHK_MEMBERROLE CHECK (MemberRole IN (0, 1))  -- ADMIN je 0, USER je 1
)
GO

CREATE TABLE Movie
(
	IDMovie INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(50),
	PublishedDate NVARCHAR(50),
	Description NVARCHAR(50),
	OrginalTitle NVARCHAR(50),
	Duration INT,
	ReleaseYear INT,
	Genre NVARCHAR(50),
	PicturePath NVARCHAR(50)
)
GO

CREATE TABLE Person
(
	IDPerson INT PRIMARY KEY IDENTITY,
	FullName NVARCHAR(50)
)
GO

CREATE TABLE Actor
(	
	PersonID INT FOREIGN KEY REFERENCES Person(IDPerson)
)
GO

CREATE TABLE Director
(		
	PersonID INT FOREIGN KEY REFERENCES Person(IDPerson)
)
GO


-----------------------------------------------------------------------
----------------------Kreiranje MOVIE procedura -----------------------

CREATE PROCEDURE createMovie
	@Title NVARCHAR(50),
	@PublishedDate NVARCHAR(50),
	@Description NVARCHAR(50),
	@OrginalTitle NVARCHAR(50),
	@Duration INT,
	@ReleaseYear INT,
	@Genre NVARCHAR(50),
	@PicturePath NVARCHAR(50),	
	@IDMovie INT OUTPUT
AS 
BEGIN 
	INSERT INTO Movie VALUES(@Title, @PublishedDate, @Description, @OrginalTitle, @Duration, @ReleaseYear, @Genre, @PicturePath)
	SET @IDMovie = SCOPE_IDENTITY()
END
GO

CREATE PROCEDURE updateMovie
	@Title NVARCHAR(50),
	@PublishedDate NVARCHAR(50),
	@Description NVARCHAR(50),
	@OrginalTitle NVARCHAR(50),
	@Duration INT,
	@ReleaseYear INT,
	@Genre NVARCHAR(50),
	@PicturePath NVARCHAR(50),	
	@IDMovie INT 
	 
AS 
BEGIN 
	UPDATE Movie SET 
		Title = @Title,
		PublishedDate = @PublishedDate,
		Description = @Description,
		OrginalTitle = @OrginalTitle,
		Duration = @Duration,
		ReleaseYear = @ReleaseYear,
		Genre = @Genre,
		PicturePath = @PicturePath		
	WHERE 
		IDMovie = @IDMovie
END
GO


CREATE PROCEDURE deleteMovie
	@IDMovie INT	 
AS 
BEGIN 
	DELETE FROM Movie
	WHERE 
		IDMovie = @IDMovie
END
GO

CREATE PROCEDURE selectMovie
	@IDMovie INT
AS 
BEGIN 
	SELECT * FROM Movie
	WHERE 
		IDMovie = @IDMovie
END
GO

CREATE PROCEDURE selectMovies
AS 
BEGIN 
	SELECT * FROM Movie
END
GO
-----------------------------------------------------------------------
----------------------Kreiranje Authentication procedura --------------

CREATE PROC userLogin	
SELECT * FROM Users
GO

CREATE PROC UserRegister
	@Username NVARCHAR(25)
	@Password NVARCHAR(100)
AS
IF exists (SELECT * FROM Users WHERE Username = @Username and Password = @Password )
	BEGIN
		SELECT 0 AS Result
	END
ELSE
	BEGIN
		INSERT INTO Users VALUES (@Username, @Password, 0)
		SELECT 1 AS Result
	END
GO

-- CREATE PROC adminCreate
-- SELECT * FROM Users
-- AS
-- IF not exists (SELECT * FROM Users WHERE isAdmin = 1)
	-- BEGIN
		-- INSERT INTO Users VALUES('admin','admin', 1)
	-- END
-- GO
-----------------------------------------------------------------------
----------------------DELETE_ALL procedura ----------------------------

CREATE PROC deleteAll
AS
DELETE FROM Movie
DELETE FROM Actor
DELETE FROM Director
GO

-----------------------------------------------------------------------
USE MOVIES
SELECT * FROM Users
SELECT * FROM Movie
SELECT * FROM Person -- Actor + Director