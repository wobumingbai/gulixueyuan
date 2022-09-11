<template>
  <div class="app-container">
    <h1>
      封面幻灯片列表
    </h1>
    <br />

    <el-button type="primary" @click="openBannerDialog()" >添加幻灯片</el-button>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="100" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <!-- <el-table-column prop="id" label="id" /> -->

      <el-table-column prop="title" label="名称" width="200" />

      <el-table-column prop="imageUrl" label="图片" width="400">
        <!--加入下面的内容,scope.row代表这一行,img是显示这一行的哪个字段,我的是img,你的自己看下-->
        <template slot-scope="scope">
          <img :src="scope.row.imageUrl" alt="" width="200" height="100" />
        </template>
      </el-table-column>

      <el-table-column prop="linkUrl" label="链接地址" width="200" />

      <el-table-column prop="gmtCreate" label="添加时间" width="200" />

      <el-table-column prop="sort" label="排序" width="100" />

      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="openEditBanner(scope.row.id)"
            >修改</el-button
          >
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

    <!-- 添加幻灯片 -->
    <el-dialog :visible.sync="dialogBannerFormVisible" title="添加幻灯片">
      <el-form :model="banner" label-width="120px">
        <el-form-item label="幻灯片标题">
          <el-input v-model="banner.title" />
        </el-form-item>

        <el-form-item label="幻灯片链接地址">
          <el-input v-model="banner.linkUrl" />
        </el-form-item>

        <el-form-item label="幻灯片排序">
          <el-input-number
            v-model="banner.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>

        <el-form-item label="课程封面">
          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API + '/eduoss/fileoss'"
            class="avatar-uploader"
          >
            <img :src="banner.imageUrl" width="300" height="200" />
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogBannerFormVisible = false">取 消</el-button>
        <el-button
          :disabled="saveBannerBtnDisabled"
          type="primary"
          @click="saveBanner"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <!-- 修改幻灯片 -->
    <el-dialog :visible.sync="EditdialogBannerFormVisible" title="修改幻灯片">
      <el-form :model="banner" label-width="120px">
        <el-form-item label="幻灯片标题">
          <el-input v-model="banner.title" />
        </el-form-item>

        <el-form-item label="幻灯片链接地址">
          <el-input v-model="banner.linkUrl" />
        </el-form-item>

        <el-form-item label="幻灯片排序">
          <el-input-number
            v-model="banner.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>

        <el-form-item label="课程封面">
          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API + '/eduoss/fileoss'"
            class="avatar-uploader"
          >
            <img :src="banner.imageUrl" width="300" height="200" />
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="EditdialogBannerFormVisible = false">取 消</el-button>
        <el-button
          :disabled="EditBannerBtnDisabled"
          type="primary"
          @click="updateBanner"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
//引入调用teacher.js文件
import banner from "@/api/edu/banner";

export default {
  //写核心代码位置
  // data:{
  // },
  data() {
    //定义变量和初始值
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
      banner: {
        title: "",
        sort: 0,
        linkUrl: "",
        imageUrl: "/static/01.jpg",
      },
      dialogBannerFormVisible: false,
      BASE_API: process.env.BASE_API,
    };
  },
  created() {
    //页面渲染之前执行，一般调用methods定义的方法
    //调用
    this.getList();
  },
  methods: {
    //创建具体的方法，调用定义的方法
    updateBanner() {
      banner.updatebanner(this.banner).then((response) => {
        //关闭弹框
        this.EditdialogBannerFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "修改幻灯片成功!",
        });

        this.banner = {
          imageUrl: "/static/01.jpg",
        };
        //刷新页面
        this.getList();
      });
    },
    openEditBanner(id) {
      //弹框
      this.EditdialogBannerFormVisible = true;
      //调用接口
      banner.getbanner(id).then((response) => {
        this.banner = response.data.item;
      });
    },
    saveBanner() {
      banner.addBanner(this.banner).then((response) => {
        this.dialogBannerFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "添加幻灯片成功!",
        });
      });

      this.getList();
    },
    //上传封面成功调用的方法
    handleAvatarSuccess(res, file) {
      this.banner.imageUrl = res.data.url;
    },
    //上传之前调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    openBannerDialog() {
      //弹框
      this.dialogBannerFormVisible = true;
      //表单数据清空
      this.banner = {
        imageUrl: "/static/01.jpg",
      };
    },
    //讲师列表的方法
    getList(page = 1) {
      this.page = page;
      banner.pageBanner(this.page, this.limit).then((response) => {
        //请求成功
        //response接口返回的数据
        //console.log(response)
        this.list = response.data.rows;
        this.total = response.data.total;
      });
    },
    resetData() {
      //清空的方法
      this.getList();
    },
    removeDataById(id) {
      this.$confirm("此操作将永久删除幻灯片, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        banner.removebanner(id).then((response) => {
          this.$message({
            type: "success",
            message: "删除幻灯片成功!",
          });
          //刷新页面
          this.getList();
        });
      });
    },
  },
};
</script>
