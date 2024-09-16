![image](https://github.com/user-attachments/assets/6db1e646-bdfd-44fa-95b9-ea97c640cedd)<h3>背景</h3>
<hr />

TODO


<br/>

<h3>框架及外部依賴</h3>

>* Java
>* SpringBoot 3.3.3
>* JDK 17
>* MySQL
>* Rabbit MQ

<br/>
		     


<h3>第一步: 安裝 reactive-system-demo </h3>
https://github.com/nick54785478/reactive-system-demo

<br/>

1. 在Maven項目或者pom.xml上右鍵 -->  Run As --> "Maven Build... " 或 Run Configuration --> "Maven Build"
2. 在"Goals"输入框中输入：clean install 
3. 使用時在Run As中選中 Maven build 即可。
4. console 出現 BUILD SUCCESS 即打包完成。
![image](https://github.com/user-attachments/assets/6cd50da8-e11f-48b4-9bf7-f5ce99e673b9)


<br/>


```
version: "3"
services:
  db:
    image: mysql:8.0
    container_name: local-mysql
    restart: always
    environment:
      TZ: Asia/Taipei
      MYSQL_ROOT_PASSWORD: root 
    command:
      --max_connections=1000
      --character-set-server=utf8mb4
      --collation-server=utf8mb4_unicode_ci
      --default-authentication-plugin=mysql_native_password
    ports:
      - 3306:3306
    volumes:
      - ./data:/var/lib/mysql
      - ./conf:/etc/mysql/conf.d
    networks:
        mysql:
          aliases:
            - mysql
networks:
  mysql:
    name: mysql
    driver: bridge
```
<br/>


**可執行下列指令建立 docker container**

```
        docker-compose up -d
```




<h3>第二步: 建立表及新增相關資料</h3>
相關表與資料如下:

```
	CREATE TABLE IF NOT EXISTS user_info (
	    `id` BIGINT(20) AUTO_INCREMENT,
	    `name` VARCHAR(100),
	    `username` VARCHAR(100),
	    `password` VARCHAR(100),
	    `address` VARCHAR(255),
	    `email` VARCHAR(100),
	    `active_flag` CHAR(1),
	    PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
	
	CREATE TABLE IF NOT EXISTS role_info (
	    `id` BIGINT(20) AUTO_INCREMENT,
	    `name` VARCHAR(100),
	    `type` VARCHAR(100),
	    `description` VARCHAR(255),
	    `active_flag` CHAR(1),
	    PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
	
	CREATE TABLE IF NOT EXISTS auth_info (
	    `id` BIGINT(20) AUTO_INCREMENT,
	    `user_id` BIGINT(20),
	    `role_id` BIGINT(20),
	    `active_flag` CHAR(1),    
	    PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

	# system 密碼 system123
	INSERT INTO user_info (name, username, password, address, email, active_flag) VALUES ('System', 'system', '$2a$10$eKT3qdUVQO1mf0.hUZswDuZiO69BKv20OjE3lPITJYqQol4MYAWNm','', 'system@example.com', 'Y');	
	INSERT INTO role_info (name, type, description, active_flag) VALUES ('ADMIN', 'ROOT', '系統管理員', 'Y');
	INSERT INTO role_info (name, type, description, active_flag) VALUES ('DATA_OWNER', 'USER', '新增、修改、刪除、讀取所有使用者、角色資料權限', 'Y');	
	INSERT INTO auth_info (user_id, role_id, active_flag) VALUES (1, 1, 'Y');
```

<br />


<h3>第三步: 使用Postman 或 WebClient 對其進行測試</h3>

**註. 請先執行第二步，新增 Admin 角色資料，之後註冊新帳號，將DATA_OWNER 權限賦予給該帳號，開始執行後續。**

> * Postman 作法:
根據 iface.handler 中的 URL 去建立 Request (有些要記得設置 Token，透過 LoginHandler 內 /login 取得 JWToken )。
> * WebClient 作法:
可參考 ReactiveSystemDemoApplicationTests，裡面有示範。


