import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { getFolders } from './QuarkSubscribeRecord.api';

//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'ID',
    align: 'center',
    dataIndex: 'id',
    width: '50px',
  },
  {
    title: '订阅名称',
    align: 'center',
    dataIndex: 'name',
  },
  {
    title: '夸克账号ID',
    align: 'center',
    dataIndex: 'accountId',
  },
  {
    title: '分享链接',
    align: 'center',
    dataIndex: 'shareUrl',
  },
  {
    title: '保存的文件夹',
    align: 'center',
    dataIndex: 'toDirFid',
  },
  {
    title: '订阅的文件夹',
    align: 'center',
    dataIndex: 'sourceDirFid',
  },
  {
    title: '文件名前缀',
    align: 'center',
    dataIndex: 'prefixName',
  },
  {
    title: '状态',
    align: 'center',
    dataIndex: 'status_dictText',
    width: '100px',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '订阅名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入订阅名称!' }];
    },
    componentProps: ({ formModel }) => {
      return {
        onChange: (e) => {
          formModel.prefixName = e.target.value + '.S01E';
        },
      };
    },
  },
  {
    label: '夸克账号ID',
    field: 'accountId',
    component: 'InputNumber',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入夸克账号ID!' }];
    },
  },
  {
    label: '分享链接',
    field: 'shareUrl',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入分享链接!' }];
    },
  },
  {
    label: '保存的文件夹',
    field: 'toDirFid',
    // component: 'Input',
    component: 'Select',
    dynamicDisabled: ({ values }) => {
      // console.log('toDirFid:' + JSON.stringify(values));
      return values.id > 0;
    },
    componentProps: {
      options: [],
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请选择保存的文件夹!' }];
    },
  },
  {
    label: '订阅的文件夹',
    field: 'sourceDirFid',
    component: 'Input',
  },
  {
    label: '文件名前缀',
    field: 'prefixName',
    component: 'Input',
  },
  {
    label: '状态',
    field: 'status',
    component: 'Select',
    componentProps: {
      options: [
        { label: '订阅中', value: 1 },
        { label: '已完结', value: 2 },
        { label: '已失效', value: 3 },
      ],
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请选择状态!' }];
    },
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];

// 动态加载 toDirFid 选项
export async function loadToDirFidOptions() {
  try {
    const response = await getFolders("0");
    // 检查 API 返回的状态和数据
    if (Array.isArray(response)) {
      return response.map((item) => ({
        label: item.fileName, // 使用 `title` 作为显示标签
        value: item.fid, // 使用 `value` 作为值
      }));
    } else {
      // 处理返回结果不符合预期的情况
      console.error('API 返回的数据格式不正确或请求失败', response);
      return [];
    }
  } catch (error) {
    // 处理 API 调用失败的情况
    console.error('API 调用失败', error);
    return [];
  }
}

// 高级查询数据
export const superQuerySchema = {
  name: { title: '订阅名称', order: 0, view: 'text', type: 'string' },
  accountId: { title: '夸克账号ID', order: 1, view: 'number', type: 'number' },
  shareUrl: { title: '分享链接', order: 2, view: 'text', type: 'string' },
  toDirFid: { title: '保存的文件夹', order: 3, view: 'text', type: 'string' },
  sourceDirFid: { title: '订阅的文件夹', order: 4, view: 'text', type: 'string' },
  prefixName: { title: '文件名前缀', order: 5, view: 'text', type: 'string' },
  status: { title: '状态', order: 6, view: 'number', type: 'number' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
