import config from "@/config"
import http from "@/utils/request"

export default {
	menu: {
		myMenus: {
			url: `${config.API_URL}/system/menu/my/1.6.1`,
			name: "获取我的菜单",
			get: async function(){
				return await http.get(this.url);
			}
		},
		list: {
			url: `${config.API_URL}/s/permission/list`,
			name: "获取菜单",
			post: async function(){
				return await http.post(this.url, {});
			}
		},
		create: {
			url: `${config.API_URL}/s/permission/create`,
			name: "新建",
			post: async function(data){
				return await http.post(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		update: {
			url: `${config.API_URL}/s/permission/update`,
			name: "修改",
			put: async function(data){
				return await http.put(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		permissionSort: {
			url: `${config.API_URL}/s/permission/sort`,
			name: "排序",
			put: async function(data){
				return await http.put(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		batchDel: {
			url: `${config.API_URL}/s/permission`,
			name: "删除",
			delete: async function(ids){
				return await http.delete(this.url+ `/batch`, ids, {
					headers: {
						//'response-status': 401
					}
				});
			}
		}
	},
	dic: {
		tree: {
			url: `${config.API_URL}/system/dic/tree`,
			name: "获取字典树",
			get: async function(){
				return await http.get(this.url);
			}
		},
		list: {
			url: `${config.API_URL}/system/dic/list`,
			name: "字典明细",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		get: {
			url: `${config.API_URL}/system/dic/get`,
			name: "获取字典数据",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	role: {
		list: {
			url: `${config.API_URL}/sRole`,
			name: "分页查询角色列表",
			post: async function(page, pageSize, params){
				return await http.post(this.url+ `/page/${page}/${pageSize}`, params);
			}
		},
		one: {
			url: `${config.API_URL}/sRole`,
			name: "id查询",
			get: async function(id){
				return await http.get(this.url+ `/retrieve/${id}`, {},{
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		create: {
			url: `${config.API_URL}/sRole/create`,
			name: "创建",
			post: async function(data){
				return await http.post(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		authorize: {
			url: `${config.API_URL}/sRole/authorize`,
			name: "授权",
			post: async function(data){
				return await http.post(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		bindUser: {
			url: `${config.API_URL}/sRole/bind/user`,
			name: "分配用户",
			post: async function(data){
				return await http.post(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		roleUser: {
			url: `${config.API_URL}/sRole/role/user`,
			name: "角色用户",
			post: async function(data){
				return await http.post(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		authority: {
			url: `${config.API_URL}/sRole/authority`,
			name: "权限",
			get: async function(id){
				return await http.get(this.url + `/${id}`, {}, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		delete: {
			url: `${config.API_URL}/sRole`,
			name: "删除",
			delete: async function(id){
				return await http.delete(this.url+ `/${id}`, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		update: {
			url: `${config.API_URL}/sRole/update`,
			name: "更新",
			put: async function(data){
				return await http.put(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		active: {
			url: `${config.API_URL}/sRole/active`,
			name: "启用/禁用",
			get: async function(id, active){
				return await http.get(this.url+ `/${id}/${active}`, {
					headers: {
						//'response-status': 401
					}
				});
			}
		}
	},
	dept: {
		list: {
			url: `${config.API_URL}/system/dept/list`,
			name: "获取部门列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	user: {
		list: {
			url: `${config.API_URL}/user`,
			name: "分页查询用户列表",
			post: async function(page, pageSize, params){
				return await http.post(this.url+ `/page/${page}/${pageSize}`, params);
			}
		},
		one: {
			url: `${config.API_URL}/user`,
			name: "id查询",
			get: async function(id){
				return await http.get(this.url+ `/retrieve/${id}`, {},{
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		create: {
			url: `${config.API_URL}/user/create`,
			name: "创建",
			post: async function(data){
				return await http.post(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		authorize: {
			url: `${config.API_URL}/user/authorize`,
			name: "授权",
			post: async function(data){
				return await http.post(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		authority: {
			url: `${config.API_URL}/user/authority`,
			name: "权限",
			get: async function(id){
				return await http.get(this.url + `/${id}`, {}, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		delete: {
			url: `${config.API_URL}/user`,
			name: "删除",
			delete: async function(id){
				return await http.delete(this.url+ `/${id}`, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		update: {
			url: `${config.API_URL}/user/update`,
			name: "更新",
			put: async function(data){
				return await http.put(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		changePassword: {
			url: `${config.API_URL}/change/password`,
			name: "修改密码",
			put: async function(data){
				return await http.put(this.url, data, {
					headers: {
						//'response-status': 401
					}
				});
			}
		},
		active: {
			url: `${config.API_URL}/user/active`,
			name: "启用/禁用",
			get: async function(id, active){
				return await http.get(this.url+ `/${id}/${active}`, {
					headers: {
						//'response-status': 401
					}
				});
			}
		}
	},
	app: {
		list: {
			url: `${config.API_URL}/system/app/list`,
			name: "应用列表",
			get: async function(){
				return await http.get(this.url);
			}
		}
	},
	log: {
		list: {
			url: `${config.API_URL}/system/log/list`,
			name: "日志列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	table: {
		list: {
			url: `${config.API_URL}/system/table/list`,
			name: "表格列管理列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		info: {
			url: `${config.API_URL}/system/table/info`,
			name: "表格列管理详情",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	tasks: {
		list: {
			url: `${config.API_URL}/system/tasks/list`,
			name: "系统任务管理",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	}
}
