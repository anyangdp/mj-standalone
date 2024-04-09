<template>
	<el-dialog title="角色权限设置" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-tabs tab-position="top">
			<el-tab-pane label="菜单权限">
				<div class="treeMain">
					<el-tree ref="menu" node-key="id" :data="menu.list" :props="menu.props" show-checkbox></el-tree>
				</div>
			</el-tab-pane>
<!--			<el-tab-pane label="数据权限">-->
<!--				<el-form label-width="100px" label-position="left">-->
<!--					<el-form-item label="规则类型">-->
<!--						<el-select v-model="data.dataType" placeholder="请选择">-->
<!--							<el-option label="本人可见" value="0"></el-option>-->
<!--							<el-option label="全部设备" value="1"></el-option>-->
<!--							<el-option label="自定义" value="2"></el-option>-->
<!--						</el-select>-->
<!--					</el-form-item>-->
<!--					<el-form-item label="选择设备" v-show="data.dataType=='2'">-->
<!--						<sc-table-select v-model="value" size="large" :apiObj="apiObj" :table-width="700" multiple clearable-->
<!--										 collapse-tags collapse-tags-tooltip :props="props" @change="change">-->
<!--							<template #header="{form, submit}">-->
<!--								<el-form :inline="true" :model="form">-->
<!--									<el-form-item>-->
<!--										<el-input v-model="form.name" placeholder="名称" clearable/>-->
<!--									</el-form-item>-->
<!--									<el-form-item>-->
<!--										<el-button type="primary" @click="submit">查询</el-button>-->
<!--									</el-form-item>-->
<!--								</el-form>-->
<!--							</template>-->
<!--							<el-table-column fixed label="设备号" prop="id" width="180"></el-table-column>-->
<!--							<el-table-column label="名称" prop="name" width="150"></el-table-column>-->
<!--							<el-table-column label="产品名称" prop="productName" width="150"></el-table-column>-->
<!--							<el-table-column label="在离线" prop="state" width="150">-->
<!--								<template #default="scope">-->
<!--									<el-space wrap size="2">-->
<!--										<span-->
<!--											:class="scope.row.state == 'ONLINE' ? 'status-dot status-success' : 'status-dot status-error'"></span>{{-->
<!--											scope.row.state == 'ONLINE' ? '在线' : '离线'-->
<!--										}}-->
<!--									</el-space>-->
<!--								</template>-->
<!--							</el-table-column>-->
<!--						</sc-table-select>-->
<!--					</el-form-item>-->
<!--				</el-form>-->
<!--			</el-tab-pane>-->
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
				dataType: "0",
				selected: [],
			},
			apiObj: this.$API.device.list,
			value: [
				{
					id: "755101012231A58B",
					name: "仁千测试设备1"
				}
			],
			props: {
				label: 'name',
				value: 'id',
				keyword: "name"
			}
		}
	},
	mounted() {
		this.getMenu()
	},
	methods: {
		open(row) {
			console.log(row)
			this.data.id = row.id
			this.visible = true;
		},
		async submit() {
			this.isSaveing = true;

			//选中的和半选的合并后传值接口
			var checkedKeys = this.$refs.menu.getCheckedKeys().concat(this.$refs.menu.getHalfCheckedKeys())
			console.log("checkedKeys: " + checkedKeys)
			// eslint-disable-next-line no-debugger
			await this.$API.system.user.authorize.post({
				userId: this.data.id,
				permissionList: checkedKeys
			})
			this.$message.success("操作成功")
			// setTimeout(() => {
			this.isSaveing = false;
			// 	this.visible = false;
			// 	this.$message.success("操作成功")
			// 	this.$emit('success')
			// }, 1000)
		},
		async getMenu() {
			var res = await this.$API.system.menu.list.post()
			console.log(this.data.id)
			this.menu.list = res
			let permission =  await this.$API.system.user.authority.get(this.data.id)
			let array = []
			if (permission) {
				permission.forEach((item) => {
					array.push(item.permissionId)
				})
			}
			//获取接口返回的之前选中的和半选的合并，处理过滤掉有叶子节点的key
			this.menu.checked = array

			this.$nextTick(() => {
				let filterKeys = this.menu.checked.filter(key => this.$refs.menu.getNode(key).isLeaf)
				this.$refs.menu.setCheckedKeys(filterKeys, true)
			})
		},
		change(val) {
			this.$message('change事件，返回详情查看控制台')
			console.log(val)
			this.data.selected.push(val)
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
