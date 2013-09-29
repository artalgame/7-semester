USE TEST_DB
GO
CREATE TABLE dbo.Goods
(
	GoodID int PRIMARY KEY NOT NULL,
	Description text,
	Price int NOT NULL,
	Quantity int NOT NULL
);
GO