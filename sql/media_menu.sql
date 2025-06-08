-- ----------------------------
-- 媒体管理菜单数据
-- ----------------------------

-- 一级菜单：媒体管理
INSERT INTO sys_menu VALUES('5', '媒体管理', '0', '5', 'media', null, '', '', 1, 0, 'M', '0', '0', '', 'video', 'admin', sysdate(), '', null, '媒体管理目录');

-- 二级菜单
INSERT INTO sys_menu VALUES('500', '媒体文件', '5', '1', 'file', 'media/file/index', '', '', 1, 0, 'C', '0', '0', 'media:file:list', 'upload', 'admin', sysdate(), '', null, '媒体文件菜单');
INSERT INTO sys_menu VALUES('501', '媒体分类', '5', '2', 'category', 'media/category/index', '', '', 1, 0, 'C', '0', '0', 'media:category:list', 'tree', 'admin', sysdate(), '', null, '媒体分类菜单');
INSERT INTO sys_menu VALUES('502', '播放统计', '5', '3', 'statistics', 'media/statistics/index', '', '', 1, 0, 'C', '0', '0', 'media:statistics:list', 'chart', 'admin', sysdate(), '', null, '播放统计菜单');

-- 媒体文件管理按钮
INSERT INTO sys_menu VALUES('5000', '媒体文件查询', '500', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'media:file:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5001', '媒体文件新增', '500', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'media:file:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5002', '媒体文件修改', '500', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'media:file:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5003', '媒体文件删除', '500', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'media:file:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5004', '媒体文件导出', '500', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'media:file:export', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5005', '媒体文件上传', '500', '6', '', '', '', '', 1, 0, 'F', '0', '0', 'media:file:upload', '#', 'admin', sysdate(), '', null, '');

-- 媒体分类管理按钮
INSERT INTO sys_menu VALUES('5010', '媒体分类查询', '501', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'media:category:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5011', '媒体分类新增', '501', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'media:category:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5012', '媒体分类修改', '501', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'media:category:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5013', '媒体分类删除', '501', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'media:category:remove', '#', 'admin', sysdate(), '', null, '');

-- 播放统计按钮
INSERT INTO sys_menu VALUES('5020', '播放统计查询', '502', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'media:statistics:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('5021', '播放统计导出', '502', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'media:statistics:export', '#', 'admin', sysdate(), '', null, '');

-- 为超级管理员角色分配媒体管理权限
INSERT INTO sys_role_menu VALUES ('1', '5');
INSERT INTO sys_role_menu VALUES ('1', '500');
INSERT INTO sys_role_menu VALUES ('1', '501');
INSERT INTO sys_role_menu VALUES ('1', '502');
INSERT INTO sys_role_menu VALUES ('1', '5000');
INSERT INTO sys_role_menu VALUES ('1', '5001');
INSERT INTO sys_role_menu VALUES ('1', '5002');
INSERT INTO sys_role_menu VALUES ('1', '5003');
INSERT INTO sys_role_menu VALUES ('1', '5004');
INSERT INTO sys_role_menu VALUES ('1', '5005');
INSERT INTO sys_role_menu VALUES ('1', '5010');
INSERT INTO sys_role_menu VALUES ('1', '5011');
INSERT INTO sys_role_menu VALUES ('1', '5012');
INSERT INTO sys_role_menu VALUES ('1', '5013');
INSERT INTO sys_role_menu VALUES ('1', '5020');
INSERT INTO sys_role_menu VALUES ('1', '5021'); 