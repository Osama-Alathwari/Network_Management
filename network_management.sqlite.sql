BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "shared_folder" (
	"ID"	INTEGER NOT NULL,
	"Folder_Name"	TEXT NOT NULL,
	"Folder_Path"	TEXT NOT NULL,
	"Descriptions"	TEXT NOT NULL,
	"date_added"	TEXT NOT NULL,
	PRIMARY KEY("ID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "network_pc" (
	"ID"	INTEGER NOT NULL,
	"PC_NAME"	TEXT NOT NULL,
	"PC_IP"	TEXT NOT NULL,
	"PC_Number"	TEXT NOT NULL,
	"Descriptions"	TEXT NOT NULL,
	"date_added"	TEXT NOT NULL,
	PRIMARY KEY("ID" AUTOINCREMENT)
);
INSERT INTO "shared_folder" VALUES (1,'Documents','/home/focal/Documents','Sharing the documents folder','2022-09-06');
INSERT INTO "network_pc" VALUES (1,'Computer-Server','127.0.0.1','1','This is the master computer of Network','2022-09-06');
INSERT INTO "network_pc" VALUES (2,'Computer-PC-1','127.0.0.1','2','Computer Network PC-1','2022-09-06');
COMMIT;
