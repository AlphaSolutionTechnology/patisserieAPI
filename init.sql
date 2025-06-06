-- Create tables
CREATE TABLE public."order" (
    id integer NOT NULL,
    order_code character varying(6) NOT NULL,
    date_time timestamp without time zone NOT NULL,
    user_code character varying(4),
    payment_method integer,
    address character varying(255),
    order_status integer
);

CREATE TABLE public.order_items (
    id integer NOT NULL,
    order_id integer,
    product_id integer NOT NULL,
    quantity integer,
    unit_price double precision
);

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying(50) DEFAULT 'roll'::character varying NOT NULL,
    category character varying(10) DEFAULT 'cake'::character varying NOT NULL,
    price double precision DEFAULT 0 NOT NULL,
    image_path character varying(100),
    description text
);

CREATE TABLE public.users (
    username character varying(30),
    password character varying(100),
    user_code character varying(4),
    id integer NOT NULL,
    phone_number character varying(11) DEFAULT '0'::character varying NOT NULL
);

-- Create sequences
CREATE SEQUENCE public.order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.order_items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Set sequence ownership
ALTER SEQUENCE public.order_id_seq OWNED BY public."order".id;
ALTER SEQUENCE public.order_items_id_seq OWNED BY public.order_items.id;
ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;
ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

-- Set default values
ALTER TABLE ONLY public."order" ALTER COLUMN id SET DEFAULT nextval('public.order_id_seq'::regclass);
ALTER TABLE ONLY public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq'::regclass);
ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);
ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);

-- Create primary keys
ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pk PRIMARY KEY (id);

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pk PRIMARY KEY (id);

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pk_2 UNIQUE (order_code);

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pk PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk UNIQUE (user_code);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk_2 PRIMARY KEY (id);

-- Create foreign keys
ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order___fk FOREIGN KEY (user_code) REFERENCES public.users(user_code);

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order__fk FOREIGN KEY (order_id) REFERENCES public."order"(id);

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_products__fk FOREIGN KEY (product_id) REFERENCES public.products(id);

-- Insert initial data
INSERT INTO public.users (username, password, user_code, id, phone_number) VALUES
('johnny', '12345', '1234', 1, '0');

INSERT INTO public.products (id, name, category, price, image_path, description) VALUES
(1, 'Bolo Valdo', 'Cakes', 40, 'assets/images/bolo_valdo.jpg', NULL),
(2, 'Bolo de Cenoura', 'Cakes', 30, 'assets/images/bolo_cenoura.jpg', NULL),
(3, 'Red Velvet', 'Cakes', 45, 'assets/images/red_velvet.jpg', NULL),
(4, 'Bolo de Chocolate Cremoso', 'Cakes', 45, 'assets/images/bolo_chocolate.jpg', NULL),
(5, 'Bolo de Morango com Chantilly', 'Cakes', 50, 'assets/images/bolo_morango.jpg', NULL),
(6, 'Bolo de Limão Siciliano', 'Cakes', 42, 'assets/images/bolo_limao.jpg', NULL),
(7, 'Bolo de Nozes com Doce de Leite', 'Cakes', 48, 'assets/images/bolo_nozes.jpg', NULL),
(8, 'Bolo de Abacaxi com Coco', 'Cakes', 40, 'assets/images/bolo_abacaxi.jpg', NULL),
(9, 'Torta Mineira', 'Pies', 10, 'assets/images/torta_mineira.jpg', NULL),
(10, 'Torta de Creme', 'Pies', 10, 'assets/images/torta_creme.jpg', NULL),
(11, 'Torta de Maçã com Canela', 'Pies', 12, 'assets/images/torta_maca.jpg', NULL),
(12, 'Torta de Chocolate com Avelã', 'Pies', 15, 'assets/images/torta_chocolate.jpg', NULL),
(13, 'Torta de Limão com Merengue', 'Pies', 13, 'assets/images/torta_limao.jpg', NULL),
(14, 'Torta de Frango Cremosa', 'Pies', 10, 'assets/images/torta_frango.jpg', NULL),
(15, 'Torta de Palmito com Catupiry', 'Pies', 14, 'assets/images/torta_palmito.jpg', NULL),
(16, 'Suco de Laranja', 'Drinks', 8, 'assets/images/suco_laranja.jpg', NULL),
(17, 'Refrigerante', 'Drinks', 6, 'assets/images/refrigerante.jpg', NULL),
(18, 'Suco de Maracujá Natural', 'Drinks', 7, 'assets/images/suco_maracuja.jpg', NULL),
(19, 'Água de Coco Gelada', 'Drinks', 6, 'assets/images/agua_coco.jpg', NULL),
(20, 'Limonada Suíça', 'Drinks', 8, 'assets/images/limonada.jpg', NULL),
(21, 'Chá Gelado de Hibisco', 'Drinks', 7, 'assets/images/cha_hibisco.jpg', NULL),
(22, 'Milkshake de Ovomaltine', 'Drinks', 12, 'assets/images/milkshake_ovomaltine.jpg', NULL),
(23, 'Brownie', 'Sweets', 10, 'assets/images/brownie.jpg', NULL),
(24, 'Brigadeiro', 'Sweets', 5, 'assets/images/brigadeiro.jpg', NULL),
(25, 'Trufa de Chocolate Meio Amargo', 'Sweets', 6, 'assets/images/trufa_chocolate.jpg', NULL),
(26, 'Cajuzinho Tradicional', 'Sweets', 4, 'assets/images/cajuzinho.jpg', NULL),
(27, 'Beijinho de Coco', 'Sweets', 4, 'assets/images/beijinho.jpg', NULL),
(28, 'Pé de Moleque Caseiro', 'Sweets', 5, 'assets/images/pe_moleque.jpg', NULL),
(29, 'Bala de Caramelo com Flor de Sal', 'Sweets', 3, 'assets/images/bala_caramelo.jpg', NULL);

-- Set sequence values
SELECT pg_catalog.setval('public.order_id_seq', 4, true);
SELECT pg_catalog.setval('public.order_items_id_seq', 7, true);
SELECT pg_catalog.setval('public.products_id_seq', 29, true);
SELECT pg_catalog.setval('public.users_id_seq', 1, true); 