/*
 * Engine: PostgreSQL
 * Version: 0.0.2
 * Description: Initial database structure and data.
 */

/*
 * Structure
 */

 ALTER TABLE poker_user ADD COLUMN
   chips INTEGER DEFAULT 10000;

