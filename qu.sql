/****** Script for SelectTopNRows command from SSMS  ******/
SELECT TOP (1000) [id]
  FROM [dbBayes].[dbo].[test]

select * from dbo.gnode

select * from gnetwork
delete from gnetwork

select * from gnode order by id

create proc setNetWork @netName nchar(100)
as
	delete from gnetwork where  gnetwork.netName = @netName
	insert into gnetwork values(@netName)

exec setNetWork 'checking1'

drop proc setNode

create proc setNode @id1 int, @idname nchar(100), @nodeName nchar(100), @outcomes varchar(250), @xPos int, @yPos int, @nodeType int, @nodeDef varchar(500), @netName varchar(250) , @parentNode varchar(250)
as
	delete from gnode where gnode.netName  = @netName and gnode.idname = @idname
	insert into gnode values (@id1,@idname, @nodeName, @outcomes,@xPos,@yPos,@nodeType, @nodeDef , @netName,@parentNode)

exec setNode 1,'test1','Testing gnode 01', 'S F M', 100,200,10, '0.1 0.2 0.3' ,'network 01', 'nodeP'

drop proc getNode

create proc getNode @netName nChar(100)
as
	select * from gnode where netName = @netName order by id


exec getNode 'Tutorial01'