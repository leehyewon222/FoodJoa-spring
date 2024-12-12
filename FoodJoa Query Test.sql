select * from mealkit_order;
select * from mealkit;

SELECT count(*)
FROM mealkit_order
WHERE id='review1' AND delivered=2;


SELECT COUNT(*)
FROM mealkit_order o  
JOIN mealkit k 
ON k.no=o.mealkit_no 
WHERE k.id='geonyongId' AND o.delivered=2;