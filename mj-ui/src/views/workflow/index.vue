<template>
	<el-container>
		<el-header>
			<div class="left-panel">
				<el-button type="primary" icon="el-icon-plus" @click="add"></el-button>
				<el-button type="danger" plain icon="el-icon-delete" :disabled="selection.length==0" @click="batch_del"></el-button>
			</div>
			<div class="right-panel">
				<div class="right-panel-search">
					<el-input v-model="search.keyword" placeholder="工作流名称" clearable></el-input>
					<el-button type="primary" icon="el-icon-search" @click="upsearch"></el-button>
				</div>
			</div>
		</el-header>
		<el-main class="nopadding">
			<scTable ref="table" :apiObj="apiObj" row-key="id" @selection-change="selectionChange" stripe>
				<el-table-column type="selection" width="50"></el-table-column>
				<el-table-column label="流程id" prop="id" width="150"></el-table-column>
				<el-table-column label="流程key" prop="key" width="150"></el-table-column>
				<el-table-column label="名称" prop="name" width="150"></el-table-column>
				<el-table-column label="版本" prop="version" width="150"></el-table-column>
				<el-table-column label="状态" prop="suspendState" width="100"></el-table-column>
				<el-table-column label="部署时间" prop="deploymentTime" width="180"></el-table-column>
				<el-table-column label="部署id" prop="deploymentId" width="180"></el-table-column>
				<el-table-column label="操作" fixed="right" align="right" width="350">
					<template #default="scope">
						<el-button plain type="primary" size="small" @click="table_show(scope.row, scope.$index)">
							查看
						</el-button>
						<el-popconfirm title="确定删除吗？" @confirm="table_del(scope.row, scope.$index)">
							<template #reference>
								<el-button  plain type="danger" size="small">删除</el-button>
							</template>
						</el-popconfirm>
					</template>
				</el-table-column>

			</scTable>
		</el-main>
	</el-container>

	<save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSaveSuccess" @closed="dialog.save=false"></save-dialog>

	<permission-dialog v-if="dialog.permission" ref="permissionDialog" @closed="dialog.permission=false"></permission-dialog>

	<user-bind v-if="dialog.userBind" ref="userBindDialog" @closed="dialog.userBind=false"></user-bind>
</template>

<script>
	import saveDialog from './save'
	import permissionDialog from './permission'
	import UserBind from "@/views/setting/role/userBind.vue";

	export default {
		name: 'role',
		components: {
			UserBind,
			saveDialog,
			permissionDialog
		},
		data() {
			return {
				dialog: {
					save: false,
					permission: false,
					userBind: false
				},
				apiObj: this.$API.system.role.list,
				selection: [],
				search: {
					keyword: null
				}
			}
		},
		methods: {
			//添加
			add(){
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open()
				})
			},
			//编辑
			table_edit(row){
				console.log(row)
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open('edit').setData(row)
				})
			},
			//查看
			table_show(row){
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open('show').setData(row)
				})
			},
			//权限设置
			userBind(row){
				this.dialog.userBind = true
				this.$nextTick(() => {
					this.$refs.userBindDialog.open().setData(row)
				})
			},
			//权限设置
			permission(row){
				this.dialog.permission = true
				this.$nextTick(() => {
					this.$refs.permissionDialog.open(row)
				})
			},
			async change(active, id) {
				await this.$API.system.role.active.get(id, active);
				this.$message.success("状态修改成功")
			},
			//删除
			async table_del(row, index) {
				await this.$API.system.role.delete.delete(row.id);
				//这里选择刷新整个表格 OR 插入/编辑现有表格数据
				this.$refs.table.tableData.splice(index, 1);
				this.$message.success("删除成功")

			},
			//批量删除
			async batch_del() {
				this.$confirm(`确定删除选中的 ${this.selection.length} 项吗？`, '提示', {
					type: 'warning'
				}).then(() => {
					const loading = this.$loading();
					this.selection.forEach(item => {
						this.$refs.table.tableData.forEach((itemI, indexI) => {
							if(item.id === itemI.id) {
								this.$refs.table.tableData.splice(indexI, 1)
							}
						})
					})
					loading.close();
					this.$message.success("操作成功")
				}).catch(() => {

				})
			},
			//表格选择后回调事件
			selectionChange(selection){
				this.selection = selection;
			},
			//表格内开关
			changeSwitch(val, row){
				row.status = row.status == '1'?'0':'1'
				row.$switch_status = true;
				setTimeout(()=>{
					delete row.$switch_status;
					row.status = val;
					this.$message.success("操作成功")
				}, 500)
			},
			//搜索
			upsearch(){
				this.$refs.table.upData(this.search)
			},
			//本地更新数据
			handleSaveSuccess(data, mode){
				if(mode == 'add') {
					data.id = new Date().getTime()
					this.$refs.table.tableData.unshift(data)
				} else if(mode == 'edit') {
					this.$refs.table.tableData.filter(item => item.id === data.id).forEach(item => {
						Object.assign(item, data)
					})
				}
			}
		}
	}
</script>

<style>
</style>
