-- ----------------------------
-- 媒体管理相关表
-- ----------------------------

-- ----------------------------
-- 1、媒体分类表
-- ----------------------------
DROP TABLE IF EXISTS `media_category`;
CREATE TABLE `media_category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父分类ID',
  `order_num` int(4) DEFAULT 0 COMMENT '显示顺序',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='媒体分类表';

-- ----------------------------
-- 初始化媒体分类数据
-- ----------------------------
INSERT INTO `media_category` VALUES (1, '音频', 0, 1, '0', 'admin', NOW(), '', NULL, '音频分类');
INSERT INTO `media_category` VALUES (2, '视频', 0, 2, '0', 'admin', NOW(), '', NULL, '视频分类');
INSERT INTO `media_category` VALUES (3, '音乐', 1, 1, '0', 'admin', NOW(), '', NULL, '音乐子分类');
INSERT INTO `media_category` VALUES (4, '播客', 1, 2, '0', 'admin', NOW(), '', NULL, '播客子分类');
INSERT INTO `media_category` VALUES (5, '电影', 2, 1, '0', 'admin', NOW(), '', NULL, '电影子分类');
INSERT INTO `media_category` VALUES (6, '短视频', 2, 2, '0', 'admin', NOW(), '', NULL, '短视频子分类');

-- ----------------------------
-- 2、媒体文件表
-- ----------------------------
DROP TABLE IF EXISTS `media_file`;
CREATE TABLE `media_file` (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `file_name` varchar(255) NOT NULL COMMENT '文件名称',
  `original_name` varchar(255) NOT NULL COMMENT '原始文件名',
  `file_path` varchar(500) NOT NULL COMMENT '文件路径',
  `file_url` varchar(500) NOT NULL COMMENT '文件访问URL',
  `file_size` bigint(20) DEFAULT 0 COMMENT '文件大小（字节）',
  `file_type` varchar(50) NOT NULL COMMENT '文件类型（audio/video）',
  `file_format` varchar(20) NOT NULL COMMENT '文件格式（mp3,mp4,avi等）',
  `duration` int(11) DEFAULT 0 COMMENT '时长（秒）',
  `thumbnail_url` varchar(500) DEFAULT NULL COMMENT '缩略图URL',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `title` varchar(200) NOT NULL COMMENT '媒体标题',
  `description` text COMMENT '媒体描述',
  `tags` varchar(500) DEFAULT NULL COMMENT '标签（逗号分隔）',
  `view_count` int(11) DEFAULT 0 COMMENT '播放次数',
  `like_count` int(11) DEFAULT 0 COMMENT '点赞数',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用 2审核中）',
  `is_public` char(1) DEFAULT '1' COMMENT '是否公开（0私有 1公开）',
  `sort_order` int(4) DEFAULT 0 COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`file_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_file_type` (`file_type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 COMMENT='媒体文件表';

-- ----------------------------
-- 3、媒体播放记录表
-- ----------------------------
DROP TABLE IF EXISTS `media_play_record`;
CREATE TABLE `media_play_record` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `file_id` bigint(20) NOT NULL COMMENT '文件ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID（可为空，支持匿名播放）',
  `device_id` varchar(100) DEFAULT NULL COMMENT '设备ID',
  `play_time` datetime NOT NULL COMMENT '播放时间',
  `play_duration` int(11) DEFAULT 0 COMMENT '播放时长（秒）',
  `ip_address` varchar(128) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  PRIMARY KEY (`record_id`),
  KEY `idx_file_id` (`file_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_play_time` (`play_time`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 COMMENT='媒体播放记录表';

-- ----------------------------
-- 4、媒体收藏表
-- ----------------------------
DROP TABLE IF EXISTS `media_favorite`;
CREATE TABLE `media_favorite` (
  `favorite_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `file_id` bigint(20) NOT NULL COMMENT '文件ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`favorite_id`),
  UNIQUE KEY `uk_user_file` (`user_id`, `file_id`),
  KEY `idx_file_id` (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 COMMENT='媒体收藏表';

-- ----------------------------
-- 5、媒体评论表
-- ----------------------------
DROP TABLE IF EXISTS `media_comment`;
CREATE TABLE `media_comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `file_id` bigint(20) NOT NULL COMMENT '文件ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父评论ID',
  `content` text NOT NULL COMMENT '评论内容',
  `like_count` int(11) DEFAULT 0 COMMENT '点赞数',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1隐藏）',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`comment_id`),
  KEY `idx_file_id` (`file_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 COMMENT='媒体评论表'; 