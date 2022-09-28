--
-- PostgreSQL database dump
--

-- Dumped from database version 12.12 (Ubuntu 12.12-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.12 (Ubuntu 12.12-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: auth_group; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.auth_group (
    id integer NOT NULL,
    name character varying(150) NOT NULL
);


ALTER TABLE public.auth_group OWNER TO user1;

--
-- Name: auth_group_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.auth_group_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_group_id_seq OWNER TO user1;

--
-- Name: auth_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.auth_group_id_seq OWNED BY public.auth_group.id;


--
-- Name: auth_group_permissions; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.auth_group_permissions (
    id bigint NOT NULL,
    group_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.auth_group_permissions OWNER TO user1;

--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.auth_group_permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_group_permissions_id_seq OWNER TO user1;

--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.auth_group_permissions_id_seq OWNED BY public.auth_group_permissions.id;


--
-- Name: auth_permission; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.auth_permission (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    content_type_id integer NOT NULL,
    codename character varying(100) NOT NULL
);


ALTER TABLE public.auth_permission OWNER TO user1;

--
-- Name: auth_permission_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.auth_permission_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_permission_id_seq OWNER TO user1;

--
-- Name: auth_permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.auth_permission_id_seq OWNED BY public.auth_permission.id;


--
-- Name: auth_user; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.auth_user (
    id integer NOT NULL,
    password character varying(128) NOT NULL,
    last_login timestamp with time zone,
    is_superuser boolean NOT NULL,
    username character varying(150) NOT NULL,
    first_name character varying(150) NOT NULL,
    last_name character varying(150) NOT NULL,
    email character varying(254) NOT NULL,
    is_staff boolean NOT NULL,
    is_active boolean NOT NULL,
    date_joined timestamp with time zone NOT NULL
);


ALTER TABLE public.auth_user OWNER TO user1;

--
-- Name: auth_user_groups; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.auth_user_groups (
    id bigint NOT NULL,
    user_id integer NOT NULL,
    group_id integer NOT NULL
);


ALTER TABLE public.auth_user_groups OWNER TO user1;

--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.auth_user_groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_groups_id_seq OWNER TO user1;

--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.auth_user_groups_id_seq OWNED BY public.auth_user_groups.id;


--
-- Name: auth_user_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.auth_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_id_seq OWNER TO user1;

--
-- Name: auth_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.auth_user_id_seq OWNED BY public.auth_user.id;


--
-- Name: auth_user_user_permissions; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.auth_user_user_permissions (
    id bigint NOT NULL,
    user_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.auth_user_user_permissions OWNER TO user1;

--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.auth_user_user_permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_user_user_permissions_id_seq OWNER TO user1;

--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.auth_user_user_permissions_id_seq OWNED BY public.auth_user_user_permissions.id;


--
-- Name: blindstick_emergencyevent; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.blindstick_emergencyevent (
    id bigint NOT NULL,
    device integer NOT NULL,
    location character varying(100) NOT NULL,
    "time" timestamp with time zone NOT NULL,
    is_processed boolean NOT NULL,
    "user" character varying(100) NOT NULL
);


ALTER TABLE public.blindstick_emergencyevent OWNER TO user1;

--
-- Name: blindstick_emergencyevent_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

ALTER TABLE public.blindstick_emergencyevent ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.blindstick_emergencyevent_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: blindstick_userprofile; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.blindstick_userprofile (
    id bigint NOT NULL,
    birthday date,
    gender character varying(6) NOT NULL,
    family_address character varying(100) NOT NULL,
    location character varying(100) NOT NULL,
    "user" character varying(64) NOT NULL
);


ALTER TABLE public.blindstick_userprofile OWNER TO user1;

--
-- Name: blindstick_userprofile_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.blindstick_userprofile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.blindstick_userprofile_id_seq OWNER TO user1;

--
-- Name: blindstick_userprofile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.blindstick_userprofile_id_seq OWNED BY public.blindstick_userprofile.id;


--
-- Name: django_admin_log; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.django_admin_log (
    id integer NOT NULL,
    action_time timestamp with time zone NOT NULL,
    object_id text,
    object_repr character varying(200) NOT NULL,
    action_flag smallint NOT NULL,
    change_message text NOT NULL,
    content_type_id integer,
    user_id integer NOT NULL,
    CONSTRAINT django_admin_log_action_flag_check CHECK ((action_flag >= 0))
);


ALTER TABLE public.django_admin_log OWNER TO user1;

--
-- Name: django_admin_log_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.django_admin_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_admin_log_id_seq OWNER TO user1;

--
-- Name: django_admin_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.django_admin_log_id_seq OWNED BY public.django_admin_log.id;


--
-- Name: django_content_type; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.django_content_type (
    id integer NOT NULL,
    app_label character varying(100) NOT NULL,
    model character varying(100) NOT NULL
);


ALTER TABLE public.django_content_type OWNER TO user1;

--
-- Name: django_content_type_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.django_content_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_content_type_id_seq OWNER TO user1;

--
-- Name: django_content_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.django_content_type_id_seq OWNED BY public.django_content_type.id;


--
-- Name: django_migrations; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.django_migrations (
    id bigint NOT NULL,
    app character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    applied timestamp with time zone NOT NULL
);


ALTER TABLE public.django_migrations OWNER TO user1;

--
-- Name: django_migrations_id_seq; Type: SEQUENCE; Schema: public; Owner: user1
--

CREATE SEQUENCE public.django_migrations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.django_migrations_id_seq OWNER TO user1;

--
-- Name: django_migrations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user1
--

ALTER SEQUENCE public.django_migrations_id_seq OWNED BY public.django_migrations.id;


--
-- Name: django_session; Type: TABLE; Schema: public; Owner: user1
--

CREATE TABLE public.django_session (
    session_key character varying(40) NOT NULL,
    session_data text NOT NULL,
    expire_date timestamp with time zone NOT NULL
);


ALTER TABLE public.django_session OWNER TO user1;

--
-- Name: auth_group id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group ALTER COLUMN id SET DEFAULT nextval('public.auth_group_id_seq'::regclass);


--
-- Name: auth_group_permissions id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group_permissions ALTER COLUMN id SET DEFAULT nextval('public.auth_group_permissions_id_seq'::regclass);


--
-- Name: auth_permission id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_permission ALTER COLUMN id SET DEFAULT nextval('public.auth_permission_id_seq'::regclass);


--
-- Name: auth_user id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user ALTER COLUMN id SET DEFAULT nextval('public.auth_user_id_seq'::regclass);


--
-- Name: auth_user_groups id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_groups ALTER COLUMN id SET DEFAULT nextval('public.auth_user_groups_id_seq'::regclass);


--
-- Name: auth_user_user_permissions id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_user_permissions ALTER COLUMN id SET DEFAULT nextval('public.auth_user_user_permissions_id_seq'::regclass);


--
-- Name: blindstick_userprofile id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.blindstick_userprofile ALTER COLUMN id SET DEFAULT nextval('public.blindstick_userprofile_id_seq'::regclass);


--
-- Name: django_admin_log id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_admin_log ALTER COLUMN id SET DEFAULT nextval('public.django_admin_log_id_seq'::regclass);


--
-- Name: django_content_type id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_content_type ALTER COLUMN id SET DEFAULT nextval('public.django_content_type_id_seq'::regclass);


--
-- Name: django_migrations id; Type: DEFAULT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_migrations ALTER COLUMN id SET DEFAULT nextval('public.django_migrations_id_seq'::regclass);


--
-- Data for Name: auth_group; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.auth_group (id, name) FROM stdin;
\.


--
-- Data for Name: auth_group_permissions; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.auth_group_permissions (id, group_id, permission_id) FROM stdin;
\.


--
-- Data for Name: auth_permission; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.auth_permission (id, name, content_type_id, codename) FROM stdin;
1	Can add log entry	1	add_logentry
2	Can change log entry	1	change_logentry
3	Can delete log entry	1	delete_logentry
4	Can view log entry	1	view_logentry
5	Can add permission	2	add_permission
6	Can change permission	2	change_permission
7	Can delete permission	2	delete_permission
8	Can view permission	2	view_permission
9	Can add group	3	add_group
10	Can change group	3	change_group
11	Can delete group	3	delete_group
12	Can view group	3	view_group
13	Can add user	4	add_user
14	Can change user	4	change_user
15	Can delete user	4	delete_user
16	Can view user	4	view_user
17	Can add content type	5	add_contenttype
18	Can change content type	5	change_contenttype
19	Can delete content type	5	delete_contenttype
20	Can view content type	5	view_contenttype
21	Can add session	6	add_session
22	Can change session	6	change_session
23	Can delete session	6	delete_session
24	Can view session	6	view_session
25	Can add User Profile	7	add_userprofile
26	Can change User Profile	7	change_userprofile
27	Can delete User Profile	7	delete_userprofile
28	Can view User Profile	7	view_userprofile
29	Can add Admin	8	add_adminuser
30	Can change Admin	8	change_adminuser
31	Can delete Admin	8	delete_adminuser
32	Can view Admin	8	view_adminuser
33	Can add emergency event	9	add_emergencyevent
34	Can change emergency event	9	change_emergencyevent
35	Can delete emergency event	9	delete_emergencyevent
36	Can view emergency event	9	view_emergencyevent
\.


--
-- Data for Name: auth_user; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.auth_user (id, password, last_login, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined) FROM stdin;
1	pbkdf2_sha256$260000$AxxXoa8swOMg70bHfvgxIR$axTAyC4DGeFy/2XqmTBgKMuNxQV0+Je43xMHSz7hg3I=	2022-09-11 13:51:27.94131+08	t	yinuo			526319623zyn@gmail.com	t	t	2022-09-11 13:45:47.537403+08
2	pbkdf2_sha256$260000$iJt4hFZZE0bAAb5BuhMYc3$4Td3BU5FKyMSgpmUqrJiLg56yzInTXbEBKstrucTnus=	\N	f	user2				f	t	2022-09-11 13:52:30+08
3	pbkdf2_sha256$390000$eaPY238uFGXE3QOVa9Amus$X+SDL+qUW86b4XcPvoGUa+vz2pWFZm8j60QjAVT5HcI=	2022-09-11 16:46:05.097413+08	f	zhao				f	t	2022-09-11 16:46:04.859154+08
4	pbkdf2_sha256$390000$ZcDVpEJqcGeM7Ef3bC9456$EFD5Fvv15sJpZBNRqvFdssGy5QN/h1h91r/IKVvFQuo=	2022-09-11 16:56:55.28979+08	f	user3				f	t	2022-09-11 16:55:32.127421+08
5	pbkdf2_sha256$390000$P7B0QevRcm8h6cbChUOmGF$mtKMDer0z+R9HJ6z0SJY55i8kmsXwgnKLPmERsj1pCs=	2022-09-11 17:36:54.540458+08	f	user4				f	t	2022-09-11 17:36:41.051434+08
6	pbkdf2_sha256$390000$65BuWBaeAXwW8sysxJDYUo$qw/35Vqkh9QJeqZdagPfVfKacxPH6FRf+od1yPYZF/k=	2022-09-11 19:22:46.750087+08	f	Norn			newemail@qq.com	f	t	2022-09-11 19:22:46.423264+08
7	pbkdf2_sha256$390000$dDZgN85kGyQtDUWU4erkk7$h3OXqJXRvts59msgAiEs45xxKp3x+qUxzjeQdaIyPh8=	2022-09-11 19:23:59.687783+08	f	Norn1			norn@qq.com	f	t	2022-09-11 19:23:59.450553+08
8	pbkdf2_sha256$390000$D5ximpz9VVAdeZ6wqWflkG$CWWCDVSktXG0toa7bSOTVc6oVSxJXqQUf5PXhBaHtuI=	2022-09-11 19:26:05.571996+08	f	112223			123232@qq.com	f	t	2022-09-11 19:26:05.342539+08
9	pbkdf2_sha256$390000$wcLjiQbKWnebRW7DfKizCT$O1ewO+t/p47c1XlLKWTA/srGGTd28RQ4fEXl9fskFK8=	2022-09-11 19:26:42.72574+08	f	112233			123232@qq.com	f	t	2022-09-11 19:26:42.497895+08
10	pbkdf2_sha256$390000$aDtwHC9LyEu8yNsPmqgiwt$aSr8LX7DTCLVILSCUGyAE3miyZd8dNd2iS2xkpbX90Q=	\N	f	leo			leo@qq.com	f	t	2022-09-11 23:32:37.52969+08
11	pbkdf2_sha256$390000$XYjpPIEgG1yLqWSNj1quwa$33iXdfqFLZJicYxUPWvTyK1pt7RkGMNJ633KhEYTjsc=	\N	f	leo1			leo1@qq.com	f	t	2022-09-11 23:33:25.308749+08
12	pbkdf2_sha256$390000$T2htGls10rn40I8e8vwXkp$RKyS7wGbZJPVsMldegVMLaGyHOXa7ZJpeQdnrprLIV0=	\N	f	leo2			leo2@qq.com	f	t	2022-09-11 23:38:11.308663+08
13	pbkdf2_sha256$390000$NHf5dzwYzbG24zBsrtfPtK$ZiCGOTLFWvSu0efmbtaXeJdir+X0ByMClbDwpqMjxgc=	2022-09-11 23:54:34.905084+08	f	leo3			leo3@qq.com	f	t	2022-09-11 23:38:56.801649+08
14	pbkdf2_sha256$390000$qwjzjIY6sxPDSNKZSGXa3h$OXc+tYYfQ3aSsNXZ3UhktweVFaOTj6xt1/EDWeK8Cj0=	\N	f	yinuozzz			yinuo@gmail.com	f	t	2022-09-11 23:57:48.45639+08
15	pbkdf2_sha256$390000$NWYWtjUwRy5DKBcBRKRJL8$lSCP5MR/sSSubEr8GfSeGaGYcpCx4VFWOkQ7EZENDpc=	2022-09-11 23:59:03.292766+08	f	yinuozz			yinuoz@gmail.com	f	t	2022-09-11 23:58:53.26293+08
16	pbkdf2_sha256$390000$6A3n2Dik1hN0F6iSVJH1Zh$xMDPFiWfgHU8DYGEcmD5ndJkkT5oxgx/3Gbch94Ar/o=	2022-09-18 14:47:59.67143+08	f	123			123@qq.com	f	t	2022-09-17 15:57:51.557097+08
17	pbkdf2_sha256$390000$3lUJoIa1eqNIzi7dbgf9Ty$xkv5VZPktM20ftaztr714R4X8T6OihuS4V+WMMFg6CM=	2022-09-27 17:45:27.317699+08	f	testuser1			testuser1@gmail.com	f	t	2022-09-27 17:45:19.476452+08
\.


--
-- Data for Name: auth_user_groups; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.auth_user_groups (id, user_id, group_id) FROM stdin;
\.


--
-- Data for Name: auth_user_user_permissions; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.auth_user_user_permissions (id, user_id, permission_id) FROM stdin;
\.


--
-- Data for Name: blindstick_emergencyevent; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.blindstick_emergencyevent (id, device, location, "time", is_processed, "user") FROM stdin;
1	1	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user1
2	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user2
3	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user3
4	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user3
5	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user4
6	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user5
7	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user6
8	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user7
9	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user7
10	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user8
11	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user9
12	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user9
13	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user10
14	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user11
15	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user12
16	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user13
17	2	-32.037806, 115.801364	2022-09-18 00:00:00+08	f	user14
\.


--
-- Data for Name: blindstick_userprofile; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.blindstick_userprofile (id, birthday, gender, family_address, location, "user") FROM stdin;
1	2022-09-16	m	xx,xx,xx	xx ,xx	user1
\.


--
-- Data for Name: django_admin_log; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.django_admin_log (id, action_time, object_id, object_repr, action_flag, change_message, content_type_id, user_id) FROM stdin;
1	2022-09-11 13:52:30.66894+08	2	user2	1	[{"added": {}}]	4	1
2	2022-09-11 13:53:35.031633+08	2	user2	2	[]	4	1
\.


--
-- Data for Name: django_content_type; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.django_content_type (id, app_label, model) FROM stdin;
1	admin	logentry
2	auth	permission
3	auth	group
4	auth	user
5	contenttypes	contenttype
6	sessions	session
7	blindstick	userprofile
8	blindstick	adminuser
9	blindstick	emergencyevent
\.


--
-- Data for Name: django_migrations; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.django_migrations (id, app, name, applied) FROM stdin;
1	contenttypes	0001_initial	2022-09-11 13:43:54.92515+08
2	auth	0001_initial	2022-09-11 13:43:55.052369+08
3	admin	0001_initial	2022-09-11 13:43:55.080058+08
4	admin	0002_logentry_remove_auto_add	2022-09-11 13:43:55.088334+08
5	admin	0003_logentry_add_action_flag_choices	2022-09-11 13:43:55.108789+08
6	contenttypes	0002_remove_content_type_name	2022-09-11 13:43:55.134452+08
7	auth	0002_alter_permission_name_max_length	2022-09-11 13:43:55.146153+08
8	auth	0003_alter_user_email_max_length	2022-09-11 13:43:55.166088+08
9	auth	0004_alter_user_username_opts	2022-09-11 13:43:55.176772+08
10	auth	0005_alter_user_last_login_null	2022-09-11 13:43:55.201111+08
11	auth	0006_require_contenttypes_0002	2022-09-11 13:43:55.204784+08
12	auth	0007_alter_validators_add_error_messages	2022-09-11 13:43:55.214575+08
13	auth	0008_alter_user_username_max_length	2022-09-11 13:43:55.237988+08
14	auth	0009_alter_user_last_name_max_length	2022-09-11 13:43:55.257443+08
15	auth	0010_alter_group_name_max_length	2022-09-11 13:43:55.285662+08
16	auth	0011_update_proxy_permissions	2022-09-11 13:43:55.303465+08
17	auth	0012_alter_user_first_name_max_length	2022-09-11 13:43:55.314038+08
18	sessions	0001_initial	2022-09-11 13:43:55.332272+08
19	blindstick	0001_initial	2022-09-11 13:44:33.051961+08
20	blindstick	0002_alter_userprofile_user_adminuser	2022-09-11 15:29:50.230252+08
21	blindstick	0003_delete_adminuser	2022-09-11 23:59:38.940546+08
22	blindstick	0004_emergencyevent	2022-09-17 14:54:55.953964+08
23	blindstick	0005_alter_emergencyevent_user	2022-09-17 15:13:42.52217+08
\.


--
-- Data for Name: django_session; Type: TABLE DATA; Schema: public; Owner: user1
--

COPY public.django_session (session_key, session_data, expire_date) FROM stdin;
w7ohgm8faes4li10y9dg5aocbqamaflo	.eJxVjMsOwiAURP-FtSEXWh7XpXu_gfC4SNVAUtqV8d-VpAvdTeacmRdzft-K2zutbknszIRmp98y-PigOki6-3prPLa6rUvgQ-EH7fzaEj0vh_t3UHwvY62lIog4ASSrIH-DzARAcgaB2mZrMmqTFaK1BqVUMcxpskqbSEEge38A3Kk24A:1oZo5v:C8ot7fDt_D24C4UBnDi9u91D0b3z3yTn6JFy8Y3m54w	2022-10-02 14:47:59.674409+08
\.


--
-- Name: auth_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.auth_group_id_seq', 1, false);


--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.auth_group_permissions_id_seq', 1, false);


--
-- Name: auth_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.auth_permission_id_seq', 36, true);


--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.auth_user_groups_id_seq', 1, false);


--
-- Name: auth_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.auth_user_id_seq', 17, true);


--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.auth_user_user_permissions_id_seq', 1, false);


--
-- Name: blindstick_emergencyevent_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.blindstick_emergencyevent_id_seq', 17, true);


--
-- Name: blindstick_userprofile_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.blindstick_userprofile_id_seq', 1, false);


--
-- Name: django_admin_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.django_admin_log_id_seq', 2, true);


--
-- Name: django_content_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.django_content_type_id_seq', 9, true);


--
-- Name: django_migrations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user1
--

SELECT pg_catalog.setval('public.django_migrations_id_seq', 23, true);


--
-- Name: auth_group auth_group_name_key; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_name_key UNIQUE (name);


--
-- Name: auth_group_permissions auth_group_permissions_group_id_permission_id_0cd325b0_uniq; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_permission_id_0cd325b0_uniq UNIQUE (group_id, permission_id);


--
-- Name: auth_group_permissions auth_group_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_group auth_group_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_pkey PRIMARY KEY (id);


--
-- Name: auth_permission auth_permission_content_type_id_codename_01ab375a_uniq; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_content_type_id_codename_01ab375a_uniq UNIQUE (content_type_id, codename);


--
-- Name: auth_permission auth_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups auth_user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_pkey PRIMARY KEY (id);


--
-- Name: auth_user_groups auth_user_groups_user_id_group_id_94350c0c_uniq; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_group_id_94350c0c_uniq UNIQUE (user_id, group_id);


--
-- Name: auth_user auth_user_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions auth_user_user_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_pkey PRIMARY KEY (id);


--
-- Name: auth_user_user_permissions auth_user_user_permissions_user_id_permission_id_14a6b632_uniq; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_permission_id_14a6b632_uniq UNIQUE (user_id, permission_id);


--
-- Name: auth_user auth_user_username_key; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_username_key UNIQUE (username);


--
-- Name: blindstick_emergencyevent blindstick_emergencyevent_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.blindstick_emergencyevent
    ADD CONSTRAINT blindstick_emergencyevent_pkey PRIMARY KEY (id);


--
-- Name: blindstick_userprofile blindstick_userprofile_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.blindstick_userprofile
    ADD CONSTRAINT blindstick_userprofile_pkey PRIMARY KEY (id);


--
-- Name: django_admin_log django_admin_log_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_pkey PRIMARY KEY (id);


--
-- Name: django_content_type django_content_type_app_label_model_76bd3d3b_uniq; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_content_type
    ADD CONSTRAINT django_content_type_app_label_model_76bd3d3b_uniq UNIQUE (app_label, model);


--
-- Name: django_content_type django_content_type_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_content_type
    ADD CONSTRAINT django_content_type_pkey PRIMARY KEY (id);


--
-- Name: django_migrations django_migrations_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_migrations
    ADD CONSTRAINT django_migrations_pkey PRIMARY KEY (id);


--
-- Name: django_session django_session_pkey; Type: CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_session
    ADD CONSTRAINT django_session_pkey PRIMARY KEY (session_key);


--
-- Name: auth_group_name_a6ea08ec_like; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_group_name_a6ea08ec_like ON public.auth_group USING btree (name varchar_pattern_ops);


--
-- Name: auth_group_permissions_group_id_b120cbf9; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_group_permissions_group_id_b120cbf9 ON public.auth_group_permissions USING btree (group_id);


--
-- Name: auth_group_permissions_permission_id_84c5c92e; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_group_permissions_permission_id_84c5c92e ON public.auth_group_permissions USING btree (permission_id);


--
-- Name: auth_permission_content_type_id_2f476e4b; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_permission_content_type_id_2f476e4b ON public.auth_permission USING btree (content_type_id);


--
-- Name: auth_user_groups_group_id_97559544; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_user_groups_group_id_97559544 ON public.auth_user_groups USING btree (group_id);


--
-- Name: auth_user_groups_user_id_6a12ed8b; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_user_groups_user_id_6a12ed8b ON public.auth_user_groups USING btree (user_id);


--
-- Name: auth_user_user_permissions_permission_id_1fbb5f2c; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_user_user_permissions_permission_id_1fbb5f2c ON public.auth_user_user_permissions USING btree (permission_id);


--
-- Name: auth_user_user_permissions_user_id_a95ead1b; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_user_user_permissions_user_id_a95ead1b ON public.auth_user_user_permissions USING btree (user_id);


--
-- Name: auth_user_username_6821ab7c_like; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX auth_user_username_6821ab7c_like ON public.auth_user USING btree (username varchar_pattern_ops);


--
-- Name: django_admin_log_content_type_id_c4bce8eb; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX django_admin_log_content_type_id_c4bce8eb ON public.django_admin_log USING btree (content_type_id);


--
-- Name: django_admin_log_user_id_c564eba6; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX django_admin_log_user_id_c564eba6 ON public.django_admin_log USING btree (user_id);


--
-- Name: django_session_expire_date_a5c62663; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX django_session_expire_date_a5c62663 ON public.django_session USING btree (expire_date);


--
-- Name: django_session_session_key_c0390e0f_like; Type: INDEX; Schema: public; Owner: user1
--

CREATE INDEX django_session_session_key_c0390e0f_like ON public.django_session USING btree (session_key varchar_pattern_ops);


--
-- Name: auth_group_permissions auth_group_permissio_permission_id_84c5c92e_fk_auth_perm; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissio_permission_id_84c5c92e_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_group_permissions auth_group_permissions_group_id_b120cbf9_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_b120cbf9_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_permission auth_permission_content_type_id_2f476e4b_fk_django_co; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_content_type_id_2f476e4b_fk_django_co FOREIGN KEY (content_type_id) REFERENCES public.django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups auth_user_groups_group_id_97559544_fk_auth_group_id; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_group_id_97559544_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_groups auth_user_groups_user_id_6a12ed8b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_6a12ed8b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_permissions auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: auth_user_user_permissions auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_log django_admin_log_content_type_id_c4bce8eb_fk_django_co; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_content_type_id_c4bce8eb_fk_django_co FOREIGN KEY (content_type_id) REFERENCES public.django_content_type(id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: django_admin_log django_admin_log_user_id_c564eba6_fk_auth_user_id; Type: FK CONSTRAINT; Schema: public; Owner: user1
--

ALTER TABLE ONLY public.django_admin_log
    ADD CONSTRAINT django_admin_log_user_id_c564eba6_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;


--
-- PostgreSQL database dump complete
--

