--
-- Application tables
--
--

CREATE TABLE owasp.user (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL
);
    
commit;