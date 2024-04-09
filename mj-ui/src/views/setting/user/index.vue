<template>
	<el-container>
		<el-header>
			<div class="left-panel">
				<el-button type="primary" icon="el-icon-plus" @click="add"></el-button>
				<el-button type="danger" plain icon="el-icon-delete" :disabled="selection.length==0"
						   @click="batch_del"></el-button>
<!--				<el-button type="primary" plain :disabled="selection.length==0">分配角色</el-button>-->
<!--				<el-button type="primary" plain :disabled="selection.length==0">密码重置</el-button>-->
			</div>
			<div class="right-panel">
				<div class="right-panel-search">
					<el-input v-model="search.username" placeholder="登录账号 / 姓名" clearable></el-input>
					<el-button type="primary" icon="el-icon-search" @click="upsearch"></el-button>
				</div>
			</div>
		</el-header>
		<el-main class="nopadding">
			<scTable ref="table" :apiObj="apiObj" @selection-change="selectionChange" stripe remoteSort remoteFilter>
				<el-table-column type="selection" width="50"></el-table-column>
				<el-table-column label="ID" prop="id" width="180" sortable='custom'></el-table-column>
				<el-table-column label="头像" width="80" column-key="filterAvatar"
								 :filters="[{text: '已上传', value: '1'}, {text: '未上传', value: '0'}]">
					<template #default="scope">
						<el-avatar :src="scope.row.avatar" size="small"></el-avatar>
					</template>
				</el-table-column>
				<el-table-column label="登录账号" prop="username" width="150" sortable='custom'></el-table-column>
				<el-table-column label="姓名" prop="nickname" width="150" sortable='custom'></el-table-column>
				<!--				<el-table-column label="所属角色" prop="groupName" width="200" sortable='custom'></el-table-column>-->
				<el-table-column label="状态" width="100">
					<template #default="scope">
						<el-switch
							@change="change($event,scope.row.id)"
							v-model="scope.row.active"
							inline-prompt
						/>
					</template>
				</el-table-column>
				<el-table-column label="加入时间" prop="createdAt" width="150" sortable='custom'></el-table-column>
				<el-table-column label="操作" fixed="right" align="right" width="350">
					<template #default="scope">
						<el-button plain type="primary" size="small" @click="table_show(scope.row, scope.$index)">
							查看
						</el-button>
						<el-button plain type="primary" size="small" @click="table_edit(scope.row, scope.$index)">
							编辑
						</el-button>
						<el-button :disabled="scope.row.username == 'admin'" plain type="primary" size="small" @click="permission(scope.row)">
							赋权
						</el-button>
						<el-popconfirm title="确定删除吗？" @confirm="table_del(scope.row, scope.$index)">
							<template #reference>
								<el-button :disabled="scope.row.username == 'admin'" plain type="danger" size="small">删除</el-button>
							</template>
						</el-popconfirm>
					</template>
				</el-table-column>

			</scTable>
		</el-main>
	</el-container>

	<save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess" @closed="dialog.save=false"></save-dialog>

	<permission-dialog v-if="dialog.permission" ref="permissionDialog" @closed="dialog.permission=false"></permission-dialog>
</template>

<script>
import saveDialog from './save'
import permissionDialog from './permission'
export default {
	name: 'user',
	components: {
		saveDialog,
		permissionDialog
	},
	data() {
		return {
			dialog: {
				save: false
			},
			apiObj: this.$API.system.user.list,
			selection: [],
			search: {
				username: null
			}
		}
	},
	watch: {
		groupFilterText(val) {
			this.$refs.group.filter(val);
		}
	},
	mounted() {

	},
	methods: {
		//添加
		add() {
			this.dialog.save = true
			this.$nextTick(() => {
				this.$refs.saveDialog.open()
			})
		},
		//编辑
		table_edit(row) {
			this.dialog.save = true
			this.$nextTick(() => {
				this.$refs.saveDialog.open('edit').setData(row)
			})
		},
		//查看
		table_show(row) {
			this.dialog.save = true
			this.$nextTick(() => {
				this.$refs.saveDialog.open('show').setData(row)
			})
		},
		//权限设置
		permission(row){
			this.dialog.permission = true
			this.$nextTick(() => {
				this.$refs.permissionDialog.open(row)
			})
		},
		//删除
		async table_del(row, index) {
			await this.$API.system.user.delete.delete(row.id);
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
		selectionChange(selection) {
			this.selection = selection;
		},
		//搜索
		upsearch() {
			this.$refs.table.upData(this.search)
		},
		//本地更新数据
		handleSuccess(data, mode) {
			if(mode == 'add') {
				data.id = new Date().getTime()
				this.$refs.table.tableData.unshift(data)
			} else if(mode == 'edit') {
				this.$refs.table.tableData.filter(item => item.id === data.id).forEach(item => {
					Object.assign(item, data)
				})
			}
		},
		async change(active, id) {
			await this.$API.system.user.active.get(id, active);
			this.$message.success("状态修改成功")
		},
	}
}
</script>

<style>
</style>
