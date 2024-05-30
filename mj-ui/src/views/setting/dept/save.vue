<template>
	<el-row :gutter="40">
		<el-col v-if="!form.id && mode == 'init'">
			<el-empty description="请选择左侧菜单后操作" :image-size="100"></el-empty>
		</el-col>
		<template v-else>
			<el-col :lg="12">
				<h2>{{form.title || "新增部门"}}</h2>
				<el-form :model="form" :rules="rules" ref="dialogForm" label-width="80px" label-position="left">
					<el-form-item label="唯一标识" prop="id">
						<el-input :disabled="mode == 'edit'" v-model="form.id" placeholder="唯一标识：英文描述"></el-input>
						<div class="el-form-item-msg">系统内唯一，且不允许修改</div>
					</el-form-item>
					<el-form-item label="显示名称" prop="title">
						<el-input v-model="form.title" clearable placeholder="菜单显示名字"></el-input>
					</el-form-item>
					<el-form-item label="上级部门" prop="parentId">
						<el-cascader v-model="form.parentId" :options="deptOptions" :props="deptProps" :show-all-levels="false" placeholder="顶级菜单" clearable disabled></el-cascader>
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
					active: "",
				},
				deptOptions: [],
				deptProps: {
					value: 'id',
					label: 'name',
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
			dept: {
				handler(){
					this.deptOptions = this.treeToMap(this.dept)
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
						name: item.name,
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
					this.$API.system.dept.create.post(this.form)
					this.$message.success("创建成功")
					this.mode = 'edit'
				} else {
					this.$API.system.dept.update.put(this.form)
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
