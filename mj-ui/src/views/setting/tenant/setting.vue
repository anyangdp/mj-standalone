<template>
	<el-dialog title="租户设置" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-tabs tab-position="top">
			<el-tab-pane label="租户资产">
				<el-form label-width="100px" label-position="left">
					<el-form-item label="绑定资产">
						<sc-table-select v-model="resource.value" size="large" :apiObj="resource.apiObj"
										 :table-width="700" multiple clearable
										 collapse-tags collapse-tags-tooltip :props="resource.props"
										 @change="resourceChange">
							<template #header="{form, submit}">
								<el-form :inline="true" :model="form">
									<el-form-item>
										<el-input v-model="form.name" placeholder="名称" clearable/>
									</el-form-item>
									<el-form-item>
										<el-button type="primary" @click="submit">查询</el-button>
									</el-form-item>
								</el-form>
							</template>
							<el-table-column fixed label="设备号" prop="id" width="180"></el-table-column>
							<el-table-column label="名称" prop="name" width="150"></el-table-column>
							<el-table-column label="产品名称" prop="productName" width="150"></el-table-column>
							<el-table-column label="在离线" prop="state" width="150">
								<template #default="scope">
									<el-space wrap size="2">
										<span
											:class="scope.row.state == 'ONLINE' ? 'status-dot status-success' : 'status-dot status-error'"></span>{{
											scope.row.state == 'ONLINE' ? '在线' : '离线'
										}}
									</el-space>
								</template>
							</el-table-column>
						</sc-table-select>
					</el-form-item>
				</el-form>
			</el-tab-pane>
			<el-tab-pane label="租户用户">
				<el-form label-width="100px" label-position="left">
					<el-form-item label="绑定用户">
						<sc-table-select v-model="user.value" size="large" :apiObj="user.apiObj" :table-width="700"
										 multiple
										 clearable
										 collapse-tags collapse-tags-tooltip :props="user.props" @change="userChange">
							<template #header="{form, submit}">
								<el-form :inline="true" :model="form">
									<el-form-item>
										<el-input v-model="form.name" placeholder="名称" clearable/>
									</el-form-item>
									<el-form-item>
										<el-button type="primary" @click="submit">查询</el-button>
									</el-form-item>
								</el-form>
							</template>
							<el-table-column fixed label="用户id" prop="id" width="180"></el-table-column>
							<el-table-column label="用户账号" prop="username" width="150"></el-table-column>
							<el-table-column label="用户昵称" prop="nickname" width="150"></el-table-column>
							<el-table-column label="启用/停用" prop="state" width="150">
								<template #default="scope">
									<el-space wrap size="2">
										<span
											:class="scope.row.active == true ? 'status-dot status-success' : 'status-dot status-error'"></span>{{
											scope.row.state == true ? '启用' : '停用'
										}}
									</el-space>
								</template>
							</el-table-column>
						</sc-table-select>
					</el-form-item>
				</el-form>
			</el-tab-pane>
		</el-tabs>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
			<el-button type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
</template>

<script>
export default {
	emits: ['success', 'closed'],
	data() {
		return {
			visible: false,
			isSaveing: false,
			menu: {
				list: [],
				checked: [],
				props: {
					label: (data) => {
						return data.title
					}
				}
			},
			data: {
				id: '',
				tenantUser: {
					tenantId: '',
					userId: []
				},
				tenantResource: {
					tenantId: '',
					list: []
				}
			},
			resource: {
				apiObj: this.$API.device.list,
				value: [],
				props: {
					label: 'name',
					value: 'id',
					keyword: "name"
				},
			},
			user: {
				apiObj: this.$API.system.user.list,
				value: [],
				props: {
					label: 'nickname',
					value: 'id',
					keyword: "nickname"
				}
			}
		}
	},
	mounted() {
		this.getMenu()
	},
	methods: {
		async open(row) {
			Object.assign(this.data, row)
			let user = await this.$API.tenant.userList.post(1, 500, {id: row.id})
			if(user.records) {
				user.records.forEach((item) => {
					this.user.value.push({
						id: item.userId,
						nickname: item.nickname
					})
				});
				this.userChange(this.user.value)

			}
			let resource = await this.$API.tenant.resourceList.post(1, 500, {id: row.id})
			if(resource.records) {
				resource.records.forEach((item) => {
					this.resource.value.push({
						id: item.resourceId,
						name: item.resourceName
					})
				});
				this.resourceChange(this.resource.value)
			}
			console.log(resource)
			this.visible = true;
		},
		async submit() {
			this.isSaveing = true;
			console.log(this.data.tenantResource)
			console.log(this.data.tenantUser)
			await this.$API.tenant.userBatchBind.post(this.data.tenantUser)
			await this.$API.tenant.resourceBatchBind.post(this.data.tenantResource)
			this.$message.success("操作成功")
			this.isSaveing = false;
		},
		async getMenu() {
			var res = await this.$API.system.menu.list.post()
			this.menu.list = res

			//获取接口返回的之前选中的和半选的合并，处理过滤掉有叶子节点的key
			// this.menu.checked = ["system", "user", "user.add", "user.edit", "user.del", "directive.edit", "other", "directive"]
			// this.$nextTick(() => {
			// 	let filterKeys = this.menu.checked.filter(key => this.$refs.menu.getNode(key).isLeaf)
			// 	this.$refs.menu.setCheckedKeys(filterKeys, true)
			// })
		},
		resourceChange(val) {
			this.$message('change事件，返回详情查看控制台')
			console.log(val)
			console.log(this.data)
			this.data.tenantResource.tenantId = this.data.id
			this.data.tenantResource.list = this.data.tenantResource.list.slice(0, 0)
			if(val.length > 0) {
				val.forEach((value) => {
					this.data.tenantResource.list.push({
						tenantId: this.data.id,
						type: 'device',
						resourceId: value.id
					})
				})
			}
			console.log(this.data.tenantResource)
		},
		userChange(val) {
			this.$message('change事件，返回详情查看控制台')
			this.data.tenantUser.tenantId = this.data.id
			this.data.tenantUser.userId = this.data.tenantUser.userId.slice(0, 0)
			if(val.length > 0) {
				val.forEach((value) => {
					this.data.tenantUser.userId.push(value.id)
				})
			}
			console.log(this.data.tenantUser)
		}
	}
}
</script>

<style scoped>
.treeMain {
	height: 280px;
	overflow: auto;
	border: 1px solid #dcdfe6;
	margin-bottom: 10px;
}
</style>
