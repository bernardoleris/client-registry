PGDMP      *                {            Clientes    16.0    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16399    Clientes    DATABASE     �   CREATE DATABASE "Clientes" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE "Clientes";
                postgres    false            �            1259    16421    clientes    TABLE       CREATE TABLE public.clientes (
    id integer NOT NULL,
    nome character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    documento character varying(255) NOT NULL,
    data_nascfund character varying(255) NOT NULL
);
    DROP TABLE public.clientes;
       public         heap    postgres    false            �            1259    16420    clientes_id_seq    SEQUENCE     �   CREATE SEQUENCE public.clientes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.clientes_id_seq;
       public          postgres    false    216            �           0    0    clientes_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.clientes_id_seq OWNED BY public.clientes.id;
          public          postgres    false    215            P           2604    16424    clientes id    DEFAULT     j   ALTER TABLE ONLY public.clientes ALTER COLUMN id SET DEFAULT nextval('public.clientes_id_seq'::regclass);
 :   ALTER TABLE public.clientes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16421    clientes 
   TABLE DATA           W   COPY public.clientes (id, nome, email, telefone, documento, data_nascfund) FROM stdin;
    public          postgres    false    216          �           0    0    clientes_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.clientes_id_seq', 5, true);
          public          postgres    false    215            R           2606    16428    clientes clientes_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.clientes DROP CONSTRAINT clientes_pkey;
       public            postgres    false    216            T           2606    16430    clientes documento_unique 
   CONSTRAINT     Y   ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT documento_unique UNIQUE (documento);
 C   ALTER TABLE ONLY public.clientes DROP CONSTRAINT documento_unique;
       public            postgres    false    216            �   o   x�3�tJ-�K,J�W�I-�,�L�r�r@\��������\NcCK(�442� #cN#}C}##.S����"���Ģ�b�0O/)�$��$�Q&�fdj6�bT� S�+     