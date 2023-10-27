CREATE OR REPLACE FUNCTION getDistance(latFrom double precision, lonFrom double precision, latTo double precision,
                                       lonTo double precision)
    RETURNS numeric AS $$
DECLARE
    R CONSTANT numeric := 6371;
    P CONSTANT numeric := pi() / 180.0;
    a          numeric;
BEGIN
    a := 0.5 - cos((latTo - latFrom) * P) / 2.0 +
         cos(latFrom * P) * cos(latTo * P) * (1 - cos((lonTo - lonFrom) * P)) / 2;
    RETURN 2 * R * asin(sqrt(a));
END;
$$ LANGUAGE plpgsql;