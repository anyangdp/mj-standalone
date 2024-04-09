<template>
	<el-row :gutter="40">
		<el-col v-if="!form.id && mode == 'init'">
			<el-empty description="请选择左侧菜单后操作" :image-size="100"></el-empty>
		</el-col>
		<template v-else>
			<el-col :lg="12">
				<h2>{{form.title || "新增菜单"}}</h2>
				<el-form :model="form" :rules="rules" ref="dialogForm" label-width="80px" label-position="left">
					<el-form-item label="唯一标识" prop="id">
						<el-input :disabled="mode == 'edit'" v-model="form.id" placeholder="唯一标识：英文描述"></el-input>
						<div class="el-form-item-msg">系统内唯一，且不允许修改</div>
					</el-form-item>
					<el-form-item label="显示名称" prop="title">
						<el-input v-model="form.title" clearable placeholder="菜单显示名字"></el-input>
					</el-form-item>
					<el-form-item label="上级菜单" prop="parentId">
						<el-cascader v-model="form.parentId" :options="menuOptions" :props="menuProps" :show-all-levels="false" placeholder="顶级菜单" clearable disabled></el-cascader>
					</el-form-item>
					<el-form-item label="类型" prop="type">
						<el-radio-group v-model="form.type">
							<el-radio-button :label=0>菜单</el-radio-button>
							<el-radio-button :label=1>目录</el-radio-button>
							<el-radio-button :label=2>按钮</el-radio-button>
						</el-radio-group>
					</el-form-item>
					<el-form-item label="别名" prop="name">
						<el-input v-model="form.name" clearable placeholder="菜单名称"></el-input>
<!--						<div class="el-form-item-msg">系统唯一且与内置组件名一致，否则导致缓存失效。如类型为Iframe的菜单，别名将代替源地址显示在地址栏</div>-->
					</el-form-item>
					<el-form-item label="菜单图标" prop="icon">
						<sc-icon-select v-model="form.icon" clearable></sc-icon-select>
					</el-form-item>
					<el-form-item v-if="form.type == 2" label="http请求方法" prop="httpMethod">
						<el-radio-group v-model="form.httpMethod">
							<el-radio-button label="POST">POST</el-radio-button>
							<el-radio-button label="GET">GET</el-radio-button>
							<el-radio-button label="PUT">PUT</el-radio-button>
							<el-radio-button label="DELETE">DELETE</el-radio-button>
						</el-radio-group>
					</el-form-item>
					<el-form-item v-if="form.type == 2" label="路径" prop="path">
						<el-input v-model="form.path" clearable placeholder=""></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="save" :loading="loading">保 存</el-button>
					</el-form-item>
				</el-form>
			</el-col>
		</template>
	</el-row>

</template>

<script>
	import scIconSelect from '@/components/scIconSelect'

	export default {
		components: {
			scIconSelect
		},
		props: {
			menu: { type: Object, default: () => {} },
		},
		data(){
			return {
				mode: "init",
				form: {
					id: "",
					parentId: "",
					name: "",
					path: "",
					title: "",
					icon: "",
					active: "",
					type: 0
				},
				menuOptions: [],
				menuProps: {
					value: 'id',
					label: 'title',
					checkStrictly: true
				},
				rules: [],
				apiListAddTemplate: {
					code: "",
					url: ""
				},
				loading: false
			}
		},
		watch: {
			menu: {
				handler(){
					this.menuOptions = this.treeToMap(this.menu)
				},
				deep: true
			}
		},
		mounted() {

		},
		methods: {
			//简单化菜单
			treeToMap(tree){
				const map = []
				tree.forEach(item => {
					var obj = {
						id: item.id,
						parentId: item.parentId,
						title: item.title,
						children: item.children&&item.children.length>0 ? this.treeToMap(item.children) : null
					}
					map.push(obj)
				})
				return map
			},
			//保存
			save(){
				this.loading = true
				if (this.mode == 'add') {
					this.$API.system.menu.create.post(this.form)
					this.$message.success("创建成功")
					this.mode = 'edit'
				} else {
					this.$API.system.menu.update.put(this.form)
					this.$message.success("修改成功")
				}
				this.loading = false
			},
			//表单注入数据
			setData(data, pid, mode){
				this.form = data
				this.form.parentId = pid
				this.mode = mode
			}
		}
	}
</script>

<style scoped>
	h2 {font-size: 17px;color: #3c4a54;padding:0 0 30px 0;}
	.apilist {border-left: 1px solid #eee;}

	[data-theme="dark"] h2 {color: #fff;}
	[data-theme="dark"] .apilist {border-color: #434343;}
</style>
