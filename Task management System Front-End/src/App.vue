<template>
  <div id="app" class="bg-light min-vh-100 d-flex flex-column">
    <header class="bg-primary text-white text-center py-4 mb-5 shadow-sm">
      <h1 class="display-6 fw-bold">Simple Task Manager</h1>
    </header>

    <div class="container my-auto">
      <div class="row g-4 justify-content-center">
        <div class="col-lg-5 col-md-6 order-md-2">
          <div class="card shadow-lg border-0 h-100">
            <div class="card-body p-4 p-md-5">
              <h2 class="card-title text-center mb-4 text-primary">{{ editing ? 'Edit Task' : 'Create New Task' }}</h2>
              <form @submit.prevent="saveTask">
                <div class="mb-3">
                  <label for="title" class="form-label text-muted">Title</label>
                  <input type="text" id="title" class="form-control" v-model="currentTask.title" required>
                </div>
                <div class="mb-3">
                  <label for="description" class="form-label text-muted">Description</label>
                  <input type="text" id="description" class="form-control" v-model="currentTask.description">
                </div>
                <div class="form-check form-switch mb-4">
                  <input type="checkbox" id="completed" class="form-check-input" v-model="currentTask.completed">
                  <label for="completed" class="form-check-label text-muted">Task Completed</label>
                </div>
                <div class="d-grid gap-2">
                  <button type="submit" class="btn btn-primary btn-lg">{{ editing ? 'Update Task' : 'Add Task' }}</button>
                  <button v-if="editing" @click="cancelEdit" class="btn btn-secondary btn-lg">Cancel</button>
                </div>
              </form>
              <div v-if="error" class="alert alert-danger mt-3 fade show">{{ error }}</div>
            </div>
          </div>
        </div>

        <div class="col-lg-7 col-md-6 order-md-1">
          <div class="card shadow-lg border-0 h-100">
            <div class="card-body p-4 p-md-5">
              <h2 class="card-title text-center mb-4 text-primary">Task List</h2>
              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead>
                  <tr class="table-light">
                    <th>Title</th>
                    <th>Status</th>
                    <th>Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="task in tasks" :key="task.id">
                    <td>
                      <span :class="{'text-decoration-line-through text-muted': task.completed}">{{ task.title }}</span>
                    </td>
                    <td>
                        <span :class="{'badge bg-success': task.completed, 'badge bg-warning text-dark': !task.completed}">
                          {{ task.completed ? 'Completed' : 'Pending' }}
                        </span>
                    </td>
                    <td>
                      <button @click="editTask(task)" class="btn btn-sm btn-info me-2 text-white">Edit</button>
                      <button @click="deleteTask(task.id)" class="btn btn-sm btn-danger">Delete</button>
                    </td>
                  </tr>
                  <tr v-if="tasks.length === 0">
                    <td colspan="3" class="text-center text-muted">No tasks found.</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <footer class="text-center text-muted mt-5 py-3">
      <div class="container">
        <p>&copy; 2024 Simple Task Manager. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const tasks = ref([]);
const currentTask = ref({ title: '', description: '', completed: false });
const editing = ref(false);
const error = ref(null);

const API_URL = 'http://localhost:8080/tasks';

// Fetch all tasks
const fetchTasks = async () => {
  try {
    const response = await axios.get(API_URL);
    tasks.value = response.data;
  } catch (err) {
    error.value = 'Failed to fetch tasks.';
  }
};

// Save a new or updated task
const saveTask = async () => {
  try {
    if (editing.value) {
      await axios.put(`${API_URL}/${currentTask.value.id}`, currentTask.value);
    } else {
      await axios.post(API_URL, currentTask.value);
    }
    resetForm();
    await fetchTasks();
  } catch (err) {
    if (err.response && err.response.data && err.response.data.title) {
      error.value = 'Validation Error: ' + err.response.data.title;
    } else {
      error.value = 'An error occurred while saving the task.';
    }
  }
};

// Set up the form for editing
const editTask = (task) => {
  editing.value = true;
  currentTask.value = { ...task };
};

// Delete a task
const deleteTask = async (id) => {
  try {
    await axios.delete(`${API_URL}/${id}`);
    await fetchTasks();
  } catch (err) {
    error.value = 'Failed to delete task.';
  }
};

// Reset the form
const resetForm = () => {
  currentTask.value = { title: '', description: '', completed: false };
  editing.value = false;
  error.value = null;
};

const cancelEdit = () => {
  resetForm();
};

onMounted(() => {
  fetchTasks();
});
</script>
