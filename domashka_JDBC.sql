CREATE TABLE IF NOT EXISTS "Homework" (
	"id" SERIAL PRIMARY KEY,
	"name" VARCHAR(50) NOT NULL,
	"description" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Lesson" (
	"id" SERIAL PRIMARY KEY,
	"name" VARCHAR(50),
	"updatedAt" timestamp default CURRENT_TIMESTAMP NOT NULL,
	"homework_id" INT NOT NULL,
	CONSTRAINT "Lesson_homework_id_uk" UNIQUE ("homework_id"),
	CONSTRAINT "Lesson_Homework_fk" FOREIGN KEY ("homework_id") REFERENCES "Homework"("id")
);
