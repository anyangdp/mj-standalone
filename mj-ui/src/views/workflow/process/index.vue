<template>
	<el-container>
		<el-header>
			<div class="left-panel">
				<el-button
					type="primary"
					icon="el-icon-plus"
					size="mini"
					@click="OnlineDrawingProcess"
				>在线绘制流程
				</el-button>
				<el-button
					type="primary"
					icon="el-icon-plus"
					size="mini"
					@click="handleImport"
				>部署流程
				</el-button>
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
	<!--bpmnjs在线流程设计器-->
	<el-dialog
		v-model="modelVisible"
		title="流程图"
		width="1024px"
		@close="modelCancel"
		append-to-body
	>
		<div style="position:relative;height: 100%;">
			<iframe
				id="iframe"
				:src="modelerUrl"
				frameborder="0"
				width="100%"
				height="720px"
				scrolling="auto"
			></iframe>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		name: 'process-definition',
		components: {
		},
		data() {
			return {
				modelerUrl: "",
				modelVisible: false,
				dialog: {
					save: false,
					permission: false,
					userBind: false
				},
				apiObj: this.$API.workflow.process.list,
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
			OnlineDrawingProcess() {
				this.modelVisible = true;
				localStorage.setItem("VUE_APP_API_BASEURL", process.env.VUE_APP_API_BASEURL)
				this.modelerUrl = "/bpmnjs/index.html?type=addBpmn";
			},
			handleImport() {
				this.upload.title = "上传模型图";
				this.upload.open = true;
			},
			//查看
			table_show(row){
				console.log(row)
				this.modelVisible = true;
				console.log(process.env.VUE_APP_API_BASEURL)
				localStorage.setItem("VUE_APP_API_BASEURL", process.env.VUE_APP_API_BASEURL)
				console.log(localStorage.getItem("VUE_APP_API_BASEURL"))
				this.modelerUrl = '/bpmnjs/index.html?type=lookBpmn&deploymentFileUUID=' + row.deploymentId + '&deploymentName=' + encodeURI(row.resourceName);
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
			modelCancel() {
				this.upsearch()
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
