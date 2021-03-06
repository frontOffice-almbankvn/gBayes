USE [master]
GO
/****** Object:  Database [dbBayes]    Script Date: 5/3/2021 11:23:18 PM ******/
CREATE DATABASE [dbBayes]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'dbBayes', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.PHUONGSS\MSSQL\DATA\dbBayes.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'dbBayes_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.PHUONGSS\MSSQL\DATA\dbBayes_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [dbBayes] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [dbBayes].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [dbBayes] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [dbBayes] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [dbBayes] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [dbBayes] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [dbBayes] SET ARITHABORT OFF 
GO
ALTER DATABASE [dbBayes] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [dbBayes] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [dbBayes] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [dbBayes] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [dbBayes] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [dbBayes] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [dbBayes] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [dbBayes] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [dbBayes] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [dbBayes] SET  DISABLE_BROKER 
GO
ALTER DATABASE [dbBayes] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [dbBayes] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [dbBayes] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [dbBayes] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [dbBayes] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [dbBayes] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [dbBayes] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [dbBayes] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [dbBayes] SET  MULTI_USER 
GO
ALTER DATABASE [dbBayes] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [dbBayes] SET DB_CHAINING OFF 
GO
ALTER DATABASE [dbBayes] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [dbBayes] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [dbBayes] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [dbBayes] SET QUERY_STORE = OFF
GO
USE [dbBayes]
GO
/****** Object:  Table [dbo].[gnetwork]    Script Date: 5/3/2021 11:23:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gnetwork](
	[netName] [nchar](100) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gnode]    Script Date: 5/3/2021 11:23:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gnode](
	[id] [int] NULL,
	[idname] [nchar](100) NULL,
	[nodeName] [nchar](100) NULL,
	[outcomes] [varchar](250) NULL,
	[xPos] [int] NULL,
	[yPos] [int] NULL,
	[nodeType] [int] NULL,
	[nodeDef] [varchar](500) NULL,
	[netName] [varchar](250) NULL,
	[parentNode] [varchar](250) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[test]    Script Date: 5/3/2021 11:23:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[test](
	[id] [nchar](10) NULL
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[getNode]    Script Date: 5/3/2021 11:23:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[getNode] @netName nChar(100)
as
	select * from gnode where netName = @netName order by id
GO
/****** Object:  StoredProcedure [dbo].[setNetWork]    Script Date: 5/3/2021 11:23:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[setNetWork] @netName nchar(100)
as
	delete from gnetwork where  gnetwork.netName = @netName
	insert into gnetwork values(@netName)
GO
/****** Object:  StoredProcedure [dbo].[setNode]    Script Date: 5/3/2021 11:23:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[setNode] @id1 int, @idname nchar(100), @nodeName nchar(100), @outcomes varchar(250), @xPos int, @yPos int, @nodeType int, @nodeDef varchar(500), @netName varchar(250) , @parentNode varchar(250)
as
	delete from gnode where gnode.netName  = @netName and gnode.idname = @idname
	insert into gnode values (@id1,@idname, @nodeName, @outcomes,@xPos,@yPos,@nodeType, @nodeDef , @netName,@parentNode)
GO
USE [master]
GO
ALTER DATABASE [dbBayes] SET  READ_WRITE 
GO
