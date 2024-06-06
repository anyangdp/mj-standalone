<template>
	<el-container>
		<el-aside width="300px" v-loading="loading">
			<el-container>
				<el-header>
					<el-input placeholder="输入关键字进行过滤" v-model="name" clearable></el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree ref="deptTree" class="menu" node-key="id" :data="deptList" :props="deptProps" draggable="false" highlight-current :expand-on-click-node="false" check-strictly show-checkbox :filter-node-method="filterNode" @node-click="deptClick">

						<template #default="{node, data}">
							<span class="custom-tree-node el-tree-node__label">
								<span class="label">
									{{ node.label }}
								</span>
								<span class="do">
									<el-icon @click.stop="add(node, data)"><el-icon-plus /></el-icon>
								</span>
							</span>
						</template>

					</el-tree>
				</el-main>
				<el-footer style="height:51px;">
					<el-button type="primary" size="small" icon="el-icon-plus" @click="add()"></el-button>
					<el-button type="danger" size="small" plain icon="el-icon-delete" @click="delDept"></el-button>
				</el-footer>
			</el-container>
		</el-aside>
		<el-container>
			<el-main class="nopadding" style="padding:20px;" ref="main">
				<save ref="save" :dept="deptList"></save>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	import tool from "@/utils/tool";

	let newMenuIndex = 1;
	import save from './save'
	export default {
		name: "dept",
		components: {
			save
		},
		data(){
			return {
				loading: false,
				deptList: [],
				deptProps: {
					label: (data)=>{
						return data.name
					}
				},
				name: ""
			}
		},
		watch: {
			name(val){
				this.$refs.deptTree.filter(val);
			}
		},
		mounted() {
			this.getDept();
		},
		methods: {
			//加载树数据
			async getDept(){
				this.loading = true
				var res = await this.$API.system.dept.list.post();
				if (res) {
					console.log(res)
				} else {
					console.error("res: ", res)
				}
				this.loading = false
				this.deptList = res;
			},
			//树点击
			deptClick(data, node){
				const pid = node.parentId ? undefined : node.parent.data.id;
				this.$refs.save.setData(data, pid, 'edit')
				this.$refs.main.$el.scrollTop = 0
			},
			//树过滤
			filterNode(value, data){
				if (!value) return true;
				var targetText = data.name;
				return targetText.indexOf(value) !== -1;
			},
			//增加
			async add(node, data){
				console.log("node:", node)
				console.log("data", data)
				var newMenuName = "未命名" + newMenuIndex++;
				var newData = {
					id: tool.uuid.snowFlakeId(),
					name: newMenuName,
					parentId: data? data.id : '',
					sort: data? data.sort - 1: 1
				}
				console.log("newData:", newData)
				this.loading = true
				this.loading = false

				this.$refs.deptTree.append(newData, node)
				this.$refs.deptTree.setCurrentKey(newData.id)
				var pid = node ? node.data.id : ""
				this.$refs.save.setData(newData, pid, 'add')
			},
			//删除菜单
			async delDept(){
				console.log("delmenu")
				var CheckedNodes = this.$refs.deptTree.getCheckedNodes()
				if(CheckedNodes.length == 0){
					this.$message.warning("请选择需要删除的项")
					return false;
				}

				var confirm = await this.$confirm('确认删除已选择的部门吗？','提示', {
					type: 'warning',
					confirmButtonText: '删除',
					confirmButtonClass: 'el-button--danger'
				}).catch(() => {})
				if(confirm != 'confirm'){
					return false
				}

				this.loading = true
				var reqData = {
					ids: CheckedNodes.map(item => item.id)
				}
				await this.$API.system.dept.batchDel.delete(reqData.ids)
				this.loading = false
				CheckedNodes.forEach(item => {
					var node = this.$refs.deptTree.getNode(item)
					if(node.isCurrent){
						this.$refs.save.setData({})
					}
					this.$refs.deptTree.remove(item)
				})
			}
		}
	}
</script>

<style scoped>
	.custom-tree-node {display: flex;flex: 1;align-items: center;justify-content: space-between;font-size: 14px;padding-right: 24px;height:100%;}
	.custom-tree-node .label {display: flex;align-items: center;;height: 100%;}
	.custom-tree-node .label .el-tag {margin-left: 5px;}
	.custom-tree-node .do {display: none;}
	.custom-tree-node .do i {margin-left:5px;color: #999;}
	.custom-tree-node .do i:hover {color: #333;}

	.custom-tree-node:hover .do {display: inline-block;}
</style>
