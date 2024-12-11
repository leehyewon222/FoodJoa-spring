SELECT 
		        r.*, 
		        COALESCE(rr.average_rating, 0) AS average_rating, 
				COALESCE(rr.review_count, 0) AS review_count, 
		        m.nickname
		    FROM recipe r
		    LEFT JOIN (
		        SELECT recipe_no, AVG(rating) AS average_rating, COUNT(rating) AS review_count
		        FROM recipe_review
		        GROUP BY recipe_no
		    ) rr ON r.no = rr.recipe_no
		    LEFT JOIN member m ON r.id = m.id;