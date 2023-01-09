package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzySQLTableToEntityClassGenerator;

public class EzySQLTableToEntityClassGeneratorTest {

    public static void main(String[] args) {
        final EzySQLTableToEntityClassGenerator generator =
            new EzySQLTableToEntityClassGenerator();
        final String entityClass = generator.generate(
            "CREATE TABLE `wp_posts` (\n" +
                "  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `post_author` bigint unsigned NOT NULL DEFAULT '0',\n" +
                "  `post_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
                "  `post_date_gmt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
                "  `post_content` longtext COLLATE utf8mb4_unicode_520_ci NOT NULL,\n" +
                "  `post_title` text COLLATE utf8mb4_unicode_520_ci NOT NULL,\n" +
                "  `post_excerpt` text COLLATE utf8mb4_unicode_520_ci NOT NULL,\n" +
                "  `post_status` varchar(20) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT 'publish',\n" +
                "  `comment_status` varchar(20) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT 'open',\n" +
                "  `ping_status` varchar(20) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT 'open',\n" +
                "  `post_password` varchar(255) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT '',\n" +
                "  `post_name` varchar(200) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT '',\n" +
                "  `to_ping` text COLLATE utf8mb4_unicode_520_ci NOT NULL,\n" +
                "  `pinged` text COLLATE utf8mb4_unicode_520_ci NOT NULL,\n" +
                "  `post_modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
                "  `post_modified_gmt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
                "  `post_content_filtered` longtext COLLATE utf8mb4_unicode_520_ci NOT NULL,\n" +
                "  `post_parent` bigint unsigned NOT NULL DEFAULT '0',\n" +
                "  `guid` varchar(255) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT '',\n" +
                "  `menu_order` int NOT NULL DEFAULT '0',\n" +
                "  `post_type` varchar(20) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT 'post',\n" +
                "  `post_mime_type` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT '',\n" +
                "  `comment_count` bigint NOT NULL DEFAULT '0',\n" +
                "  PRIMARY KEY (`ID`),\n" +
                "  KEY `post_name` (`post_name`(191)),\n" +
                "  KEY `type_status_date` (`post_type`,`post_status`,`post_date`,`ID`),\n" +
                "  KEY `post_parent` (`post_parent`),\n" +
                "  KEY `post_author` (`post_author`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;\n"
        );
        System.out.println(entityClass);
    }
}
