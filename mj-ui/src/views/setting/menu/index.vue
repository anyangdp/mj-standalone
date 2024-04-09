<template>
	<el-container>
		<el-aside width="300px" v-loading="menuloading">
			<el-container>
				<el-header>
					<el-input placeholder="输入关键字进行过滤" v-model="menuFilterText" clearable></el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree ref="menu" class="menu" node-key="id" :data="menuList" :props="menuProps" draggable="false" highlight-current :expand-on-click-node="false" check-strictly show-checkbox :filter-node-method="menuFilterNode" @node-click="menuClick">

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
					<el-button type="danger" size="small" plain icon="el-icon-delete" @click="delMenu"></el-button>
				</el-footer>
			</el-container>
		</el-aside>
		<el-container>
			<el-main class="nopadding" style="padding:20px;" ref="main">
				<save ref="save" :menu="menuList"></save>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	let newMenuIndex = 1;
	import save from './save'

	export default {
		name: "settingMenu",
		components: {
			save
		},
		data(){
			return {
				menuloading: false,
				menuList: [],
				menuProps: {
					label: (data)=>{
						return data.title
					}
				},
				menuFilterText: ""
			}
		},
		watch: {
			menuFilterText(val){
				this.$refs.menu.filter(val);
			}
		},
		mounted() {
			this.getMenu();
		},
		methods: {
			//加载树数据
			async getMenu(){
				this.menuloading = true
				var res = await this.$API.system.menu.list.post();
				this.menuloading = false
				this.menuList = res;
			},
			//树点击
			menuClick(data, node){
				var pid = node.level==1?undefined:node.parent.data.id;
				this.$refs.save.setData(data, pid, 'edit')
				this.$refs.main.$el.scrollTop = 0
			},
			//树过滤
			menuFilterNode(value, data){
				if (!value) return true;
				var targetText = data.title;
				return targetText.indexOf(value) !== -1;
			},
			//树拖拽
			// async nodeDrop(draggingNode, dropNode, dropType) {
			// 	console.log("draggingNode: ", draggingNode)
			// 	console.log("dropNode: ", dropNode)
			// 	console.log("dropType: ", dropType)
			// 	let form = {
			// 		draggingNode: draggingNode.data,
			// 		dropNode: dropNode.data,
			// 		dropType: dropType
			// 	}
			// 	await this.$API.system.menu.permissionSort.put(form)
			// 	this.$refs.save.setData({})
			// 	this.$message(`拖拽对象：${draggingNode.data.title}, 释放对象：${dropNode.data.title}, 释放对象的位置：${dropType}`)
			// },
			//增加
			async add(node, data){
				console.log("node:", node)
				console.log("data", data)
				var newMenuName = "未命名" + newMenuIndex++;
				var newMenuData = {
					id: newMenuName,
					name: newMenuName,
					parentId: data? data.id : '',
					path: "",
					component: "",
					title: newMenuName,
					level: data ? data.level + 1: 1,
					type: 0,
					sort: data? data.sort - 1: 1
				}
				this.menuloading = true
				// this.$API.system.menu.create.post(newMenuData)
				this.menuloading = false

				this.$refs.menu.append(newMenuData, node)
				this.$refs.menu.setCurrentKey(newMenuData.id)
				var pid = node ? node.data.id : ""
				this.$refs.save.setData(newMenuData, pid, 'add')
			},
			//删除菜单
			async delMenu(){
				console.log("delmenu")
				var CheckedNodes = this.$refs.menu.getCheckedNodes()
				if(CheckedNodes.length == 0){
					this.$message.warning("请选择需要删除的项")
					return false;
				}

				var confirm = await this.$confirm('确认删除已选择的菜单吗？','提示', {
					type: 'warning',
					confirmButtonText: '删除',
					confirmButtonClass: 'el-button--danger'
				}).catch(() => {})
				if(confirm != 'confirm'){
					return false
				}

				this.menuloading = true
				var reqData = {
					ids: CheckedNodes.map(item => item.id)
				}
				await this.$API.system.menu.batchDel.delete(reqData.ids)
				this.menuloading = false
				CheckedNodes.forEach(item => {
					var node = this.$refs.menu.getNode(item)
					if(node.isCurrent){
						this.$refs.save.setData({})
					}
					this.$refs.menu.remove(item)
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
