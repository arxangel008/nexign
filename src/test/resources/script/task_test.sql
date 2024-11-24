INSERT INTO task(id, name, state, execution_time, create_date, end_date, error_description, processing_date)
VALUES (-1, '1', 'COMPLETED', 100, now(), now(), null, now()),
       (-2, '2', 'CREATED', 100, now(), null, null, null),
       (-3, '3', 'COMPLETED', 100, now(), now(), null, now()),
       (-4, '4', 'CREATED', 100, now(), null, null, null),
       (-5, '5', 'CREATED', 100, now(), null, null, null),
       (-6, '6', 'COMPLETED', 100, now(), now(), null, now());