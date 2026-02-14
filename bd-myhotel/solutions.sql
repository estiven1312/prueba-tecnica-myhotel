-- Problema 1

SELECT CASE
           WHEN SALARY < 3500 THEN 'SEGMENTO A'
           WHEN SALARY >= 3500 AND SALARY < 8000 THEN 'SEGMENTO B'
           ELSE 'SEGMENTO C' END AS SEGMENT,
       COUNT(*)                  AS TOTAL_EMPLOYEES
FROM employees
GROUP BY SEGMENT
ORDER BY CASE SEGMENT WHEN 'SEGMENTO A' THEN 1 WHEN 'SEGMENTO B' THEN 2 ELSE 3 END;


-- Problema 2
SELECT dep.DEPARTMENT_ID,
       dep.DEPARTMENT_NAME,
       CASE
           WHEN SALARY < 3500 THEN 'SEGMENTO A'
           WHEN SALARY >= 3500 AND SALARY < 8000 THEN 'SEGMENTO B'
           ELSE 'SEGMENTO C' END AS SEGMENT,
       COUNT(*)                  AS TOTAL_EMPLOYEES
FROM employees emp
         INNER JOIN departments dep ON emp.department_id = dep.department_id
GROUP BY dep.DEPARTMENT_ID, dep.DEPARTMENT_NAME, SEGMENT
ORDER BY CASE SEGMENT WHEN 'SEGMENTO A' THEN 1 WHEN 'SEGMENTO B' THEN 2 ELSE 3 END;


-- Problema 3

WITH ranked_employees AS (SELECT e.*,
                                 DENSE_RANK() OVER (
                                     PARTITION BY e.DEPARTMENT_ID
                                     ORDER BY e.SALARY DESC
                                     ) AS rn
                          FROM employees e
                          WHERE e.DEPARTMENT_ID IS NOT NULL)
SELECT d.DEPARTMENT_ID,
       d.DEPARTMENT_NAME,
       re.EMPLOYEE_ID,
       re.FIRST_NAME,
       re.LAST_NAME,
       re.JOB_ID,
       re.SALARY
FROM ranked_employees re
         JOIN departments d
              ON d.DEPARTMENT_ID = re.DEPARTMENT_ID
WHERE re.rn = 1
ORDER BY d.DEPARTMENT_ID;


-- Problema 4

WITH manager_jobs AS (SELECT j.JOB_ID
                      FROM jobs j
                      WHERE UPPER(TRIM(j.JOB_TITLE)) LIKE '%MANAGER%')
SELECT e.EMPLOYEE_ID,
       e.FIRST_NAME,
       e.LAST_NAME,
       e.JOB_ID,
       e.HIRE_DATE,
       TIMESTAMPDIFF(YEAR, e.HIRE_DATE, CURDATE()) AS YEARS_WORKED,
       e.SALARY,
       e.DEPARTMENT_ID,
       e.MANAGER_ID
FROM employees e
         INNER JOIN manager_jobs mj
                    ON mj.JOB_ID = e.JOB_ID
WHERE TIMESTAMPDIFF(YEAR, e.HIRE_DATE, CURDATE()) > 15;

-- Problema 5

SELECT d.DEPARTMENT_ID,
       d.DEPARTMENT_NAME,
       COUNT(e.EMPLOYEE_ID)    AS TOTAL_EMPLOYEES,
       ROUND(AVG(e.SALARY), 2) AS AVG_SALARY
FROM departments d
         INNER JOIN employees e
                    ON e.DEPARTMENT_ID = d.DEPARTMENT_ID
GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME
HAVING COUNT(e.EMPLOYEE_ID) > 10
ORDER BY AVG_SALARY DESC;


-- Problema 6

SELECT c.COUNTRY_ID,
       c.COUNTRY_NAME,
       COUNT(e.EMPLOYEE_ID)                                             AS TOTAL_EMPLOYEES,
       ROUND(AVG(e.SALARY), 2)                                          AS AVG_SALARY,
       MAX(e.SALARY)                                                    AS UPPER_SALARY,
       MIN(e.SALARY)                                                    AS LOWER_SALARY,
       ROUND(AVG(TIMESTAMPDIFF(MONTH, e.HIRE_DATE, CURDATE())) / 12, 2) AS AVG_YEARS_OF_ACTIVITY
FROM employees e
         INNER JOIN departments d ON d.DEPARTMENT_ID = e.DEPARTMENT_ID
         INNER JOIN locations l ON l.LOCATION_ID = d.LOCATION_ID
         INNER JOIN countries c ON c.COUNTRY_ID = l.COUNTRY_ID
GROUP BY c.COUNTRY_ID, c.COUNTRY_NAME
ORDER BY c.COUNTRY_NAME;