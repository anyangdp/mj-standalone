// 静态路由配置
// 书写格式与动态路由格式一致，全部经由框架统一转换
// 比较动态路由在meta中多加入了role角色权限，为数组类型。一个菜单是否有权限显示，取决于它以及后代菜单是否有权限。
// routes 显示在左侧菜单中的路由(显示顺序在动态路由之前)
// 示例如下

const routes = [
	{
		"name": "home",
		"path": "/home",
		"meta": {"title": "首页", "icon": "el-icon-eleme-filled", "type": "menu"},
		"children": [{
			"name": "dashboard",
			"path": "/dashboard",
			"meta": {"title": "控制台", "icon": "el-icon-menu", "affix": true},
			"component": "home"
		}, {
			"name": "userCenter",
			"path": "/usercenter",
			"meta": {"title": "帐号信息", "icon": "el-icon-user", "tag": "NEW", "role": ["ROLE_admin", "account"]},
			"component": "userCenter"
		}]
	},
	{
		"name": "setting",
		"path": "/setting",
		"meta": {"title": "配置", "icon": "el-icon-setting", "type": "menu"},
		"children": [{
			"path": "/setting/system",
			"name": "system",
			"meta": {"title": "系统设置", "icon": "el-icon-tools", "type": "menu"},
			"component": "setting/system"
		}, {
			"path": "/setting/user",
			"name": "user",
			meta: {"title": "用户管理", "icon": "el-icon-user-filled", "type": "menu", role: ["ROLE_admin", "user-manage"]},
			component: "setting/user"
		}, {
			"path": "/setting/tenant",
			"name": "tenant",
			meta: {
				"title": "租户管理",
				"icon": "el-icon-user-filled",
				"type": "menu",
				role: ["ROLE_admin", "tenant-manage"]
			},
			"component": "setting/tenant"
		}, {
			"path": "/setting/role",
			"name": "role",
			"meta": {"title": "角色管理", "icon": "el-icon-notebook", "type": "menu"},
			"component": "setting/role"
		}, {
			"path": "/setting/menu",
			"name": "settingMenu",
			meta: {
				"title": "菜单管理",
				"icon": "el-icon-fold",
				"type": "menu",
				role: ["ROLE_admin", "permission-manage"]
			},
			"component": "setting/menu"
		}
		]
	},
	{
		name: "log",
		path: "/log",
		meta: {"title": "日志管理", "icon": "el-icon-warning", "type": "menu"},
		children: [
			{
				path: "/log/access",
				name: "log-access",
				meta: {"title": "访问日志", "icon": "el-icon-warning", "type": "menu"},
				component: "log/access"
			},
			{
				path: "/log/system",
				name: "log-system",
				meta: {"title": "系统日志", "icon": "el-icon-warning", "type": "menu"},
				component: "log/system"
			}
		]
	}
]

// const routes = []

export default routes;
