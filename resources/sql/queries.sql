-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, pass)
VALUES (:id, :pass)

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id

-- :name save-file! :! :n
-- :doc create a new file record
INSERT INTO files
(owner, type, name, data)
VALUES (:owner, :type, :name, :data)

-- :name list-thumbnails :? :*
-- :doc selects thumbnail names for the given gallery owner
SELECT owner, name FROM files
  WHERE owner = :owner
    AND name LIKE 'thumb\_%'

-- :name get-image :? :1
-- :doc retrieve image data by name
SELECT type, data FROM files
WHERE name = :name

-- :name select-gallery-previews :? :*
-- :doc selects a thumbnail for each user gallery
WITH summary AS (
    SELECT f.owner,
           f.name,
           ROW_NUMBER() OVER(PARTITION BY f.owner
                                 ORDER BY f.name DESC) AS rk
      FROM files f WHERE name like 'thumb\_%')
SELECT s.*
  FROM summary s
 WHERE s.rk = 1
 
-- :name delete-file! :! :1
-- :doc deletes the file with the given name and owner
DELETE FROM files
WHERE name = :name
AND owner = :owner

-- :name delete-user-images! :! :n
-- :doc deletes all the images for the specified user
DELETE FROM files
WHERE owner = :owner
